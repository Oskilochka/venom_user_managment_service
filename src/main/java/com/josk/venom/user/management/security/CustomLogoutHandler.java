package com.josk.venom.user.management.security;

import com.josk.venom.user.management.service.jwt.JwtService;
import com.josk.venom.user.management.service.jwt.JwtServiceImpl;
import com.josk.venom.user.management.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    private final JwtService jwtService;
    private final UserService userService;

    public CustomLogoutHandler(JwtServiceImpl jwtServiceImpl, @Lazy UserService userService) {
        this.jwtService = jwtServiceImpl;
        this.userService = userService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = jwtService.extractJwtFromRequest(request);

        if (token != null) {
            String username = jwtService.extractUserName(token);
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                userService.updateLastLogout(username);
            }
        }
    }
}
