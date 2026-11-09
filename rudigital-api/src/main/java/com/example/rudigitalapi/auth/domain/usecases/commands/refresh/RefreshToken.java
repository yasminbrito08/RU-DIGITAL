package com.example.rudigitalapi.auth.domain.usecases.commands.refresh;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.rudigitalapi.auth.infra.tokens.RefreshTokenService;
import com.example.rudigitalapi.security.jwt.JwtService;
import com.example.rudigitalapi.shared.exceptions.InvalidCredentialsException;
import com.example.rudigitalapi.users.infra.queries.IGetUser;

@Component
public class RefreshToken implements IRefreshToken {

    private final RefreshTokenService refreshTokenService;
    private final IGetUser getUser;
    private final JwtService jwtService;

    public RefreshToken(
            RefreshTokenService refreshTokenService,
            IGetUser getUser,
            JwtService jwtService
    ) {
        this.refreshTokenService = refreshTokenService;
        this.getUser = getUser;
        this.jwtService = jwtService;
    }

    @Override
    public RefreshTokenOutput execute(RefreshTokenInput input) {
        UUID userId = refreshTokenService.validateAndRevoke(input.refreshToken());

        IGetUser.UserIdentity user = getUser.byId(userId)
                .orElseThrow(InvalidCredentialsException::new);

        if (!Boolean.TRUE.equals(user.isActive())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtService.generateAccessToken(user.id(), user.userType());
        String newRefreshToken = refreshTokenService.create(user.id());

        return new RefreshTokenOutput(accessToken, newRefreshToken);
    }
}
