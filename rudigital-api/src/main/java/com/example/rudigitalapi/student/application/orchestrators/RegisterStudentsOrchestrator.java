package com.example.rudigitalapi.student.application.orchestrators;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.rudigitalapi.student.application.dto.CreateStudentRequest;
import com.example.rudigitalapi.student.domain.usecases.commands.create.ICreateStudent;
import com.example.rudigitalapi.users.domain.enums.UserType;
import com.example.rudigitalapi.users.domain.usecases.commands.create.ICreateUser;

@Component
public class RegisterStudentsOrchestrator {

    private final ICreateUser createUser;
    private final ICreateStudent createStudent;

    public RegisterStudentsOrchestrator(ICreateUser createUser, ICreateStudent createStudent) {
        this.createUser = createUser;
        this.createStudent = createStudent;
    }

    @Transactional
    public RegisterStudentOutput execute(CreateStudentRequest request) {
        ICreateUser.CreateUserOutput userPersisted = createUser.execute(
                new ICreateUser.CreateUserInput(
                        request.login(),
                        request.password(),
                        UserType.STUDENT,
                        true
                )
        );

        ICreateStudent.CreateStudentOutput studentPersisted = createStudent.execute(
                new ICreateStudent.CreateStudentInput(
                        userPersisted.userId(),
                        request.registrationNumber(),
                        request.name(),
                        request.cpf(),
                        request.course(),
                        request.classGroup(),
                        request.shift(),
                        request.enrollmentStatus(),
                        request.qrCode()
                )
        );

        return new RegisterStudentOutput(studentPersisted.id());
    }

    public record RegisterStudentOutput(UUID studentId) {
    }
}
