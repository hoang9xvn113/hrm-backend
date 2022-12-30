package com.datn.hrm.application.absence.repository;

import com.datn.hrm.application.absence.entity.ApplicationAbsenceEntity;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.setting.application.absence.entity.AbsenceReasonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationAbsenceRepository extends JpaRepository<ApplicationAbsenceEntity, Long> {

    Optional<ApplicationAbsenceEntity> findByName(String name);

    Page<ApplicationAbsenceEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);

    Page<ApplicationAbsenceEntity> getAllByEmployee(EmployeeEntity employee, Pageable pageable);

    List<ApplicationAbsenceEntity> getAllByDateBetweenAndReason(Date fromDate, Date toDate, AbsenceReasonEntity absenceReasonEntity);
}
