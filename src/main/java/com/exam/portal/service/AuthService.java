package com.exam.portal.service;

import com.exam.portal.dto.AuthResponse;
import com.exam.portal.dto.LoginRequest;
import com.exam.portal.dto.RegisterRequest;
import com.exam.portal.exception.EnableTokenException;
import com.exam.portal.model.NotificationEmail;
import com.exam.portal.model.Role;
import com.exam.portal.model.User;
import com.exam.portal.model.VerificationToken;
import com.exam.portal.repository.UserRepository;
import com.exam.portal.repository.VerificationTokenRepository;
import com.exam.portal.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Async
    public void save(RegisterRequest registerRequest) throws Exception {
        if(userRepository.findByEmail(registerRequest.email()).isPresent()) {
            log.info("User Already Exist");
            throw new Exception("User Already Exist");
        }
        User user = new User();
        user.setRoles(Role.USER);
        user.setEmail(registerRequest.email());
        user.setName(registerRequest.name());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account", user.getEmail(),
                "Thank you for signing up to exam portal, \n" +
                        "please click on the below url to activate your account : \n" +
                        "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void enableAccount(String token) {
        Optional<VerificationToken> verificationTokenOption = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationTokenOption.orElseThrow(() -> new EnableTokenException("Invalid Token")));
    }

    private void fetchUserAndEnable(@NotNull VerificationToken verificationToken) {
        String email = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EnableTokenException("User is not Founded " + email));
        user.setEnabled(true);
//        verificationTokenRepository.delete(verificationToken);
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return AuthResponse.builder()
                .authenticationToken(token)
                .expiresAt(Instant.now().plus(1, ChronoUnit.HALF_DAYS))
                .username(loginRequest.email())
                .build();
    }
}
