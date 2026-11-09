package com.example.rudigitalapi.student.infra.persistence;

import org.springframework.stereotype.Component;

import com.example.rudigitalapi.student.domain.enums.EnrollmentStatus;
import com.example.rudigitalapi.student.domain.usecases.commands.create.ICreateStudent;

@Component
public class PersistStudentRepository implements IPersistStudentRepository {

    private final IStudentJpaRepository studentJpaRepository;

    public PersistStudentRepository(IStudentJpaRepository studentJpaRepository) {
        this.studentJpaRepository = studentJpaRepository;
    }

    @Override
    public ICreateStudent.CreateStudentOutput persist(ICreateStudent.CreateStudentInput input) {
        StudentEntity entity = new StudentEntity();
        entity.setUserId(input.userId());
        entity.setRegistrationNumber(input.registrationNumber());
        entity.setName(input.name());
        entity.setCpf(input.cpf());
        entity.setCourse(input.course());
        entity.setClassGroup(input.classGroup());
        entity.setShift(input.shift());
        entity.setEnrollmentStatus(
                input.enrollmentStatus() != null ? input.enrollmentStatus() : EnrollmentStatus.ACTIVE
        );
        entity.setQrCode(input.qrCode());

        StudentEntity saved = studentJpaRepository.save(entity);
        return new ICreateStudent.CreateStudentOutput(saved.getId());
    }
}
