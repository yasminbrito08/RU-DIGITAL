package com.example.rudigitalapi.employee.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateEmployeeRequest(
        @NotBlank String login,
        @NotBlank @Size(min = 6) String password,
        @NotBlank String siapeNumber,
        @NotBlank String name
) {
}
