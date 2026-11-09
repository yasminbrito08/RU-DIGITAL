package com.example.rudigitalapi.auth.application.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rudigitalapi.auth.domain.usecases.commands.login.ILogin;
import com.example.rudigitalapi.auth.domain.usecases.commands.refresh.IRefreshToken;
import com.example.rudigitalapi.auth.infra.http.dto.LoginRequest;
import com.example.rudigitalapi.auth.infra.http.dto.RefreshTokenRequest;
import com.example.rudigitalapi.security.jwt.AuthCookieService;
import com.example.rudigitalapi.shared.exceptions.InvalidCredentialsException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final ILogin login;
    private final IRefreshToken refreshToken;
    private final AuthCookieService authCookieService;

    public AuthController(
            ILogin login,
            IRefreshToken refreshToken,
            AuthCookieService authCookieService
    ) {
        this.login = login;
        this.refreshToken = refreshToken;
        this.authCookieService = authCookieService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        ILogin.LoginOutput output = login.execute(
                new ILogin.LoginInput(request.login(), request.password())
        );
        authCookieService.setTokenCookies(response, output.accessToken(), output.refreshToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
            @RequestBody(required = false) RefreshTokenRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse response
    ) {
        String rawRefreshToken = authCookieService.extractRefreshToken(httpRequest)
                .orElseGet(() -> request != null ? request.refreshToken() : null);

        if (rawRefreshToken == null || rawRefreshToken.isBlank()) {
            throw new InvalidCredentialsException();
        }

        IRefreshToken.RefreshTokenOutput output = refreshToken.execute(
                new IRefreshToken.RefreshTokenInput(rawRefreshToken)
        );
        authCookieService.setTokenCookies(response, output.accessToken(), output.refreshToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authCookieService.clearTokenCookies(response);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
