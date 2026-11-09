package com.example.rudigitalapi.employee.application.orchestrators;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.rudigitalapi.employee.application.dto.CreateEmployeeRequest;
import com.example.rudigitalapi.employee.domain.usecases.commands.create.ICreateEmployee;
import com.example.rudigitalapi.users.domain.enums.UserType;
import com.example.rudigitalapi.users.domain.usecases.commands.create.ICreateUser;

@Component
public class RegisterEmployeesOrchestrator {

    private final ICreateUser createUser;
    private final ICreateEmployee createEmployee;

    public RegisterEmployeesOrchestrator(ICreateUser createUser, ICreateEmployee createEmployee) {
        this.createUser = createUser;
        this.createEmployee = createEmployee;
    }

    @Transactional
    public RegisterEmployeeOutput execute(CreateEmployeeRequest request) {
        ICreateUser.CreateUserOutput userPersisted = createUser.execute(
                new ICreateUser.CreateUserInput(
                        request.login(),
                        request.password(),
                        UserType.EMPLOYEE,
                        true
                )
        );

        ICreateEmployee.CreateEmployeeOutput employeePersisted = createEmployee.execute(
                new ICreateEmployee.CreateEmployeeInput(
                        userPersisted.userId(),
                        request.siapeNumber(),
                        request.name()
                )
        );

        return new RegisterEmployeeOutput(employeePersisted.id());
    }

    public record RegisterEmployeeOutput(UUID employeeId) {
    }
}
