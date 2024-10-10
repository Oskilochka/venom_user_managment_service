package com.josk.venom.user.management.service.auth;

import com.josk.venom.user.management.dto.LoginRequestDto;
import com.josk.venom.user.management.dto.RegisterRequestDto;
import com.josk.venom.user.management.dto.JwtAuthenticationResponseDto;
import com.josk.venom.user.management.entity.Role;
import com.josk.venom.user.management.entity.User;
import com.josk.venom.user.management.exception.UsernameAlreadyExistsException;
import com.josk.venom.user.management.mapper.UserMapper;
import com.josk.venom.user.management.repository.UserRepository;
import com.josk.venom.user.management.service.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public JwtAuthenticationResponseDto register(RegisterRequestDto registerRequestDto) {
        User user = userMapper.toEntity(registerRequestDto);

        boolean isUsernameExist = userRepository.existsByUsername(user.getUsername());
        boolean isEmailExist = userRepository.existsByEmail(user.getEmail());

        if (isUsernameExist) {
            throw new UsernameAlreadyExistsException("Username is already taken.");
        }
        if (isEmailExist) {
            throw new UsernameAlreadyExistsException("Email is already taken.");
        }

        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        var jwt = jwtService.generateToken(savedUser);
        return JwtAuthenticationResponseDto.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponseDto.builder().token(jwt).build();
    }
}

