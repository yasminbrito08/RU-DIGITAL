package com.example.rudigitalapi.users.domain.usecases.commands.create;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.rudigitalapi.shared.exceptions.ConflictException;
import com.example.rudigitalapi.users.infra.commands.IPersistUserRepository;
import com.example.rudigitalapi.users.infra.repositories.IUserJpaRepository;

@Component
public class CreateUser implements ICreateUser {

    private final IPersistUserRepository persistUserRepository;
    private final IUserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUser(
            IPersistUserRepository persistUserRepository,
            IUserJpaRepository userJpaRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.persistUserRepository = persistUserRepository;
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CreateUserOutput execute(CreateUserInput input) {
        if (userJpaRepository.existsByLogin(input.login())) {
            throw new ConflictException("Login already in use");
        }

        String passwordHash = passwordEncoder.encode(input.password());
        return persistUserRepository.persist(input, passwordHash);
    }
}
