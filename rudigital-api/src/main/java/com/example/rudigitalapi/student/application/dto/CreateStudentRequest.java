package com.example.rudigitalapi.student.application.dto;

import com.example.rudigitalapi.student.domain.enums.EnrollmentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateStudentRequest(
        @NotBlank String login,
        @NotBlank @Size(min = 6) String password,
        @NotBlank String registrationNumber,
        @NotBlank String name,
        @NotBlank String cpf,
        @NotBlank String course,
        @NotBlank String classGroup,
        @NotBlank String shift,
        @NotNull EnrollmentStatus enrollmentStatus,
        String qrCode
) {
}
