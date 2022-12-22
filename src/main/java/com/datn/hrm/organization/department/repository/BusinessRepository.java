package com.datn.hrm.organization.department.repository;

import com.datn.hrm.organization.department.entity.BusinessEntity;
import com.datn.hrm.organization.department.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, Long> {

    Optional<BusinessEntity> findByName(String name);
}
