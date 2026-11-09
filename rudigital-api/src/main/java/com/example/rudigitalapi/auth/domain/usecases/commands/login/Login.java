package com.example.rudigitalapi.auth.domain.usecases.commands.login;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.rudigitalapi.auth.infra.tokens.RefreshTokenService;
import com.example.rudigitalapi.security.jwt.JwtService;
import com.example.rudigitalapi.shared.exceptions.InvalidCredentialsException;
import com.example.rudigitalapi.users.infra.queries.IGetUser;

@Component
public class Login implements ILogin {

    private final IGetUser getUser;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public Login(
            IGetUser getUser,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        this.getUser = getUser;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public LoginOutput execute(LoginInput input) {
        IGetUser.UserIdentity user = getUser.byLogin(input.login())
                .orElseThrow(InvalidCredentialsException::new);

        if (!Boolean.TRUE.equals(user.isActive())) {
            throw new InvalidCredentialsException();
        }

        if (!passwordEncoder.matches(input.password(), user.passwordHash())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtService.generateAccessToken(user.id(), user.userType());
        String refreshToken = refreshTokenService.create(user.id());

        return new LoginOutput(accessToken, refreshToken);
    }
}
