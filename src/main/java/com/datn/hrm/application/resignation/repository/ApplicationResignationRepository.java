package com.datn.hrm.application.resignation.repository;

import com.datn.hrm.application.resignation.entity.ApplicationResignationEntity;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationResignationRepository extends JpaRepository<ApplicationResignationEntity, Long> {

    Optional<ApplicationResignationEntity> findByName(String name);

    Page<ApplicationResignationEntity> getAllByEmployee(EmployeeEntity employee, Pageable pageable);

//    Page<ApplicationResignationEntity> searchAllByNameContainingIgnoreCase(Pageable pageable);
}
