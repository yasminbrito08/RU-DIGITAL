package com.example.rudigitalapi.student.domain.usecases.commands.create;

import org.springframework.stereotype.Component;

import com.example.rudigitalapi.student.infra.persistence.IPersistStudentRepository;

@Component
public class CreateStudent implements ICreateStudent {

    private final IPersistStudentRepository persistStudentRepository;

    public CreateStudent(IPersistStudentRepository persistStudentRepository) {
        this.persistStudentRepository = persistStudentRepository;
    }

    @Override
    public CreateStudentOutput execute(CreateStudentInput input) {
        return persistStudentRepository.persist(input);
    }
}
