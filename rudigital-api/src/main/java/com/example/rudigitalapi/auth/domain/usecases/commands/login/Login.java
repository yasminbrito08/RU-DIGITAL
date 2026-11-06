package com.example.rudigitalapi.auth.domain.usecases.commands.login;

import org.springframework.stereotype.Component;

@Component
public class Login implements ILogin{
    //private loaduseridentity
    //private passwordEncoder
    //refreshTokenService

    @Override
    public LoginOutput execute(LoginInput input) {
        //faz a busca do usuario by input.login

        //verifica se ela está ativa, e se não tiver dá um throw invalidCredentials

        //verifica se o password dá metch, se não, dá invalidCredentials

        //tokens

        //retorna tokens e refreshtokens
        return null;
    }
}
