package com.example.rudigitalapi.users.infra.queries;

import java.util.Optional;
import java.util.UUID;

import com.example.rudigitalapi.users.domain.enums.UserType;

public interface IGetUser {

    Optional<UserIdentity> byLogin(String login);

    Optional<UserIdentity> byId(UUID id);

    record UserIdentity(
            UUID id,
            String login,
            String passwordHash,
            UserType userType,
            Boolean isActive
    ) {
    }
}
