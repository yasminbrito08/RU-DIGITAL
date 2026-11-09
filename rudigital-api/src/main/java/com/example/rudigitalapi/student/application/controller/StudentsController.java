package com.example.rudigitalapi.student.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.rudigitalapi.student.application.dto.CreateStudentRequest;
import com.example.rudigitalapi.student.application.dto.CreateStudentResponse;
import com.example.rudigitalapi.student.application.orchestrators.RegisterStudentsOrchestrator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/students")
public class StudentsController {

    private final RegisterStudentsOrchestrator registerStudentsOrchestrator;

    public StudentsController(RegisterStudentsOrchestrator registerStudentsOrchestrator) {
        this.registerStudentsOrchestrator = registerStudentsOrchestrator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateStudentResponse create(@Valid @RequestBody CreateStudentRequest request) {
        RegisterStudentsOrchestrator.RegisterStudentOutput output =
                registerStudentsOrchestrator.execute(request);
        return new CreateStudentResponse(output.studentId());
    }
}
