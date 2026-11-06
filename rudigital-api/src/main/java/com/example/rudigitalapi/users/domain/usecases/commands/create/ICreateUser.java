package com.example.rudigitalapi.users.domain.usecases.commands.create;

import com.example.rudigitalapi.users.domain.enums.UserType;

import java.util.UUID;

public interface ICreateUser {

    public CreateUserOutput execute(CreateUserInput input);

    public record CreateUserInput(
        String login,
        String password,
        UserType userType,
        Boolean isActive
    ){};
    public record CreateUserOutput(UUID string){};
}
