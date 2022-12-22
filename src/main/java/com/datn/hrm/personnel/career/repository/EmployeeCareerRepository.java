package com.datn.hrm.personnel.career.repository;

import com.datn.hrm.personnel.career.entity.EmployeeCareerEntity;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeCareerRepository extends JpaRepository<EmployeeCareerEntity, Long> {

    Page<EmployeeCareerEntity> getAllByEmployeeEntity(EmployeeEntity employeeEntity, Pageable pageable);
}
