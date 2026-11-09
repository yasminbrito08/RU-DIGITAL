package com.example.rudigitalapi.users.infra.commands;

import com.example.rudigitalapi.users.domain.usecases.commands.create.ICreateUser;

public interface IPersistUserRepository {

    ICreateUser.CreateUserOutput persist(ICreateUser.CreateUserInput input, String passwordHash);
}
