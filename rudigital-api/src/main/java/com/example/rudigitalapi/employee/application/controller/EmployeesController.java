package com.example.rudigitalapi.employee.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.rudigitalapi.employee.application.dto.CreateEmployeeRequest;
import com.example.rudigitalapi.employee.application.dto.CreateEmployeeResponse;
import com.example.rudigitalapi.employee.application.orchestrators.RegisterEmployeesOrchestrator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeesController {

    private final RegisterEmployeesOrchestrator registerEmployeesOrchestrator;

    public EmployeesController(RegisterEmployeesOrchestrator registerEmployeesOrchestrator) {
        this.registerEmployeesOrchestrator = registerEmployeesOrchestrator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEmployeeResponse create(@Valid @RequestBody CreateEmployeeRequest request) {
        RegisterEmployeesOrchestrator.RegisterEmployeeOutput output =
                registerEmployeesOrchestrator.execute(request);
        return new CreateEmployeeResponse(output.employeeId());
    }
}
