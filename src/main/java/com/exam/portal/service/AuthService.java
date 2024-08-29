package com.exam.portal.service;

import com.exam.portal.dto.RegisterRequest;
import com.exam.portal.exception.EnableTokenException;
import com.exam.portal.model.NotificationEmail;
import com.exam.portal.model.Role;
import com.exam.portal.model.User;
import com.exam.portal.model.VerificationToken;
import com.exam.portal.repository.UserRepository;
import com.exam.portal.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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

    @Async
    public void save(RegisterRequest registerRequest) throws Exception {
        if(userRepository.findByEmail(registerRequest.email()).isPresent()) {
            log.info("User Already Exist");
            throw new Exception("User Already Exist");
        }
        User user = new User();
        HashSet<Role> set = new HashSet<>();
        set.add(new Role("USER"));
        user.setRoles(set);
        user.setEmail(registerRequest.email());
        user.setPassword(registerRequest.password());
        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account", user.getEmail(),
                "Thank you for signing up to exam portal, \n" +
                        "please click on the below url to activate your account : \n" +
                        "http://localhost:8080/api/v1/accountVerification/" + token));
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
        verificationTokenRepository.delete(verificationToken);
        userRepository.save(user);
    }
}
