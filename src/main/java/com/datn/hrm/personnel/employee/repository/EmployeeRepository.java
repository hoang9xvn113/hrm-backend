package com.datn.hrm.personnel.employee.repository;

import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Page<EmployeeEntity> searchAllByNameContainingIgnoreCaseOrderByModifiedDateDesc(String name, Pageable pageable);
}
