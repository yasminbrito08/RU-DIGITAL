package com.example.rudigitalapi.employee.domain.usecases.commands.create;

import java.util.UUID;

public interface ICreateEmployee {

    CreateEmployeeOutput execute(CreateEmployeeInput input);

    record CreateEmployeeInput(
            UUID userId,
            String siapeNumber,
            String name
    ) {
    }

    record CreateEmployeeOutput(UUID id) {
    }
}
