package com.example.rudigitalapi.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.rudigitalapi.users.core.share.CurrentUser;
import com.example.rudigitalapi.users.core.share.current_user_details;
import com.example.rudigitalapi.users.domain.enums.UserType;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthCookieService authCookieService;

    public JwtAuthenticationFilter(JwtService jwtService, AuthCookieService authCookieService) {
        this.jwtService = jwtService;
        this.authCookieService = authCookieService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            resolveToken(request).ifPresent(token -> {
                if (!jwtService.isValid(token)) {
                    return;
                }

                UUID userId = jwtService.extractUserId(token);
                UserType userType = jwtService.extractUserType(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + userType.name()))
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                current_user_details.setContext(new CurrentUser(userId));
            });

            filterChain.doFilter(request, response);
        } finally {
            current_user_details.clearContext();
            SecurityContextHolder.clearContext();
        }
    }

    private java.util.Optional<String> resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String bearerToken = header.substring(7).trim();
            if (!bearerToken.isBlank()) {
                return java.util.Optional.of(bearerToken);
            }
        }
        return authCookieService.extractAccessToken(request);
    }
}
