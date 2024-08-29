package com.exam.portal.controller;

import com.exam.portal.dto.RegisterRequest;
import com.exam.portal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest) throws Exception {
        authService.save(registerRequest);
    }

    @GetMapping("/accountVerification/{token}")
    public String enableAccount(@PathVariable String token) {
        authService.enableAccount(token);
        return "Account is Enabled";
    }
}
