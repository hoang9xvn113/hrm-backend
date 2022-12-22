package com.datn.hrm.setting.personnel.allowance.repository;

import com.datn.hrm.setting.personnel.allowance.entity.AllowanceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllowanceRepository extends JpaRepository<AllowanceEntity, Long> {

    Optional<AllowanceEntity> findByName(String name);

    Page<AllowanceEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);
}
