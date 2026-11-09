package com.example.rudigitalapi.users.infra.commands;

import org.springframework.stereotype.Component;

import com.example.rudigitalapi.users.domain.usecases.commands.create.ICreateUser;
import com.example.rudigitalapi.users.infra.entities.UserEntity;
import com.example.rudigitalapi.users.infra.repositories.IUserJpaRepository;

@Component
public class PersistUserRepository implements IPersistUserRepository {

    private final IUserJpaRepository jpaRepository;

    public PersistUserRepository(IUserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ICreateUser.CreateUserOutput persist(ICreateUser.CreateUserInput input, String passwordHash) {
        UserEntity entity = new UserEntity();
        entity.setLogin(input.login());
        entity.setPasswordHash(passwordHash);
        entity.setUserType(input.userType());
        entity.setIsActive(input.isActive() != null ? input.isActive() : true);

        UserEntity saved = jpaRepository.save(entity);
        return new ICreateUser.CreateUserOutput(saved.getId());
    }
}
