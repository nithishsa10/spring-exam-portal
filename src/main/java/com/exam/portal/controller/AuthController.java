package com.exam.portal.controller;

import com.exam.portal.dto.AuthResponse;
import com.exam.portal.dto.LoginRequest;
import com.exam.portal.dto.RegisterRequest;
import com.exam.portal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        authService.save(registerRequest);
        return new ResponseEntity<>("User Registration Successful", OK);
    }

    @GetMapping("/accountVerification/{token}")
    public String enableAccount(@PathVariable String token) {
        authService.enableAccount(token);
        return "Activated";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/1")
    public String demo() {
        return "Finally";
    }
}
