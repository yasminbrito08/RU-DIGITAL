package com.example.rudigitalapi.student.domain.usecases.commands.create;

import java.util.UUID;

import com.example.rudigitalapi.student.domain.enums.EnrollmentStatus;

public interface ICreateStudent {

    CreateStudentOutput execute(CreateStudentInput input);

    record CreateStudentInput(
            UUID userId,
            String registrationNumber,
            String name,
            String cpf,
            String course,
            String classGroup,
            String shift,
            EnrollmentStatus enrollmentStatus,
            String qrCode
    ) {
    }

    record CreateStudentOutput(UUID id) {
    }
}
