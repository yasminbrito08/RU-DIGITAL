package com.example.rudigitalapi.users.domain.usecases.commands.create;

import com.example.rudigitalapi.users.infra.commands.IPersistUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class CreateUser implements ICreateUser{
    private IPersistUserRepository persistUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public CreateUserOutput execute(CreateUserInput input) {
        String passwordHash = CompletableFuture.supplyAsync(() -> {
            return passwordEncoder.encode(input.password());
        }, ForkJoinPool.commonPool()).join();

        return persistUserRepository.persist(input, passwordHash);
    }

}
