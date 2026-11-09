package com.example.rudigitalapi.users.infra.queries;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.rudigitalapi.users.infra.entities.UserEntity;
import com.example.rudigitalapi.users.infra.repositories.IUserJpaRepository;

@Component
public class GetUser implements IGetUser {

    private final IUserJpaRepository jpaRepository;

    public GetUser(IUserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<UserIdentity> byLogin(String login) {
        return jpaRepository.findByLogin(login).map(this::toIdentity);
    }

    @Override
    public Optional<UserIdentity> byId(UUID id) {
        return jpaRepository.findById(id).map(this::toIdentity);
    }

    private UserIdentity toIdentity(UserEntity user) {
        return new UserIdentity(
                user.getId(),
                user.getLogin(),
                user.getPasswordHash(),
                user.getUserType(),
                Boolean.TRUE.equals(user.getActive())
        );
    }
}
