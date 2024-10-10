package com.josk.venom.user.management.service.auth;

import com.josk.venom.user.management.dto.LoginRequestDto;
import com.josk.venom.user.management.dto.RegisterRequestDto;
import com.josk.venom.user.management.dto.JwtAuthenticationResponseDto;

public interface AuthenticationService {
    JwtAuthenticationResponseDto register(RegisterRequestDto request);

    JwtAuthenticationResponseDto login(LoginRequestDto request);
}
