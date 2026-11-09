package com.example.rudigitalapi.auth.domain.usecases.commands.login;

public interface ILogin {

    LoginOutput execute(LoginInput input);

    record LoginInput(String login, String password) {
    }

    record LoginOutput(String accessToken, String refreshToken) {
    }
}
