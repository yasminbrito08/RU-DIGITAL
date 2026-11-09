package com.example.rudigitalapi.student.infra.persistence;

import com.example.rudigitalapi.student.domain.usecases.commands.create.ICreateStudent;

public interface IPersistStudentRepository {

    ICreateStudent.CreateStudentOutput persist(ICreateStudent.CreateStudentInput input);
}
