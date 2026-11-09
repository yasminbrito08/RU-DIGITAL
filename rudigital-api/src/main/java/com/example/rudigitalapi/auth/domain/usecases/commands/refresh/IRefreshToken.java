package com.example.rudigitalapi.auth.domain.usecases.commands.refresh;

public interface IRefreshToken {

    RefreshTokenOutput execute(RefreshTokenInput input);

    record RefreshTokenInput(String refreshToken) {
    }

    record RefreshTokenOutput(String accessToken, String refreshToken) {
    }
}
