package com.example.rudigitalapi.employee.infra.persistence;

import com.example.rudigitalapi.employee.domain.usecases.commands.create.ICreateEmployee;

public interface IPersistEmployeeRepository {

    ICreateEmployee.CreateEmployeeOutput persist(ICreateEmployee.CreateEmployeeInput input);
}
