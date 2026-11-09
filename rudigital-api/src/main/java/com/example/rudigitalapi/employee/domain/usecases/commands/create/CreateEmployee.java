package com.example.rudigitalapi.employee.domain.usecases.commands.create;

import org.springframework.stereotype.Component;

import com.example.rudigitalapi.employee.infra.persistence.IPersistEmployeeRepository;

@Component
public class CreateEmployee implements ICreateEmployee {

    private final IPersistEmployeeRepository persistEmployeeRepository;

    public CreateEmployee(IPersistEmployeeRepository persistEmployeeRepository) {
        this.persistEmployeeRepository = persistEmployeeRepository;
    }

    @Override
    public CreateEmployeeOutput execute(CreateEmployeeInput input) {
        return persistEmployeeRepository.persist(input);
    }
}
