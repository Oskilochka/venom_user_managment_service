package com.josk.venom.user.management.security;

import com.josk.venom.user.management.entity.JwtAuthenticationToken;
import com.josk.venom.user.management.service.jwt.JwtServiceImpl;
import com.josk.venom.user.management.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtServiceImpl jwtServiceImpl;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtServiceImpl jwtServiceImpl, @Lazy UserService userService) {
        this.jwtServiceImpl = jwtServiceImpl;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            jwt = authorizationHeader.substring(7);
            username = jwtServiceImpl.extractUserName(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

            if (jwtServiceImpl.isTokenValid(jwt, userDetails)) {
                JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());

                jwtAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
