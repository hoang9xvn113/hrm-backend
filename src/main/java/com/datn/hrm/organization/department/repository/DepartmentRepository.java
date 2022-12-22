package com.datn.hrm.organization.department.repository;

import com.datn.hrm.organization.department.entity.BusinessEntity;
import com.datn.hrm.organization.department.entity.DepartmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    Page<DepartmentEntity> searchAllByParentIdAndNameContainingIgnoreCase(long parentId, String search, Pageable pageable);
    Optional<DepartmentEntity> findByNameAndParentId(String name, long parentId);

    Optional<DepartmentEntity> findByCodeAndParentId(String code, long parentId);

    List<DepartmentEntity> findAllByParentId(long parentId);
}
