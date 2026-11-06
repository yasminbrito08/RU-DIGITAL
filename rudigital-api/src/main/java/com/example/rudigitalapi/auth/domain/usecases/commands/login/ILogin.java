package com.example.rudigitalapi.auth.domain.usecases.commands.login;

public interface ILogin {

    public LoginOutput execute(LoginInput input);

    public record LoginInput(String login, String password){}
    public record LoginOutput(String accessToken, String refreshToken){}
}
