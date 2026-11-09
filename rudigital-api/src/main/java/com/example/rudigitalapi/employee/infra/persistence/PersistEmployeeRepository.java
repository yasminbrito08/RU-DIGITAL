package com.example.rudigitalapi.employee.infra.persistence;

import org.springframework.stereotype.Component;

import com.example.rudigitalapi.employee.domain.usecases.commands.create.ICreateEmployee;

@Component
public class PersistEmployeeRepository implements IPersistEmployeeRepository {

    private final IEmployeeJpaRepository employeeJpaRepository;

    public PersistEmployeeRepository(IEmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @Override
    public ICreateEmployee.CreateEmployeeOutput persist(ICreateEmployee.CreateEmployeeInput input) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setUserId(input.userId());
        entity.setSiapeNumber(input.siapeNumber());
        entity.setName(input.name());

        EmployeeEntity saved = employeeJpaRepository.save(entity);
        return new ICreateEmployee.CreateEmployeeOutput(saved.getId());
    }
}
