package com.example.rudigitalapi.users.domain.usecases.commands.create;

import java.util.UUID;

import com.example.rudigitalapi.users.domain.enums.UserType;

public interface ICreateUser {

    CreateUserOutput execute(CreateUserInput input);

    record CreateUserInput(
            String login,
            String password,
            UserType userType,
            Boolean isActive
    ) {
    }

    record CreateUserOutput(UUID userId) {
    }
}
