package com.example.rudigitalapi.employee.infra.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeJpaRepository extends JpaRepository<EmployeeEntity, UUID> {
}
