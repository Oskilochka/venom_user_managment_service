package com.josk.venom.user.management.controller;

import com.josk.venom.user.management.dto.LoginRequestDto;
import com.josk.venom.user.management.dto.RegisterRequestDto;
import com.josk.venom.user.management.dto.JwtAuthenticationResponseDto;
import com.josk.venom.user.management.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    private ResponseEntity<JwtAuthenticationResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(authenticationService.register(registerRequestDto));
    }

    @PostMapping("/login")
    private ResponseEntity<JwtAuthenticationResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authenticationService.login(loginRequestDto));
    }
}
