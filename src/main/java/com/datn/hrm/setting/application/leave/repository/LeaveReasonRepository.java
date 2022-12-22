package com.datn.hrm.setting.application.leave.repository;

import com.datn.hrm.setting.application.leave.entity.LeaveReasonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveReasonRepository extends JpaRepository<LeaveReasonEntity, Long> {

    Optional<LeaveReasonEntity> findByName(String name);

    Page<LeaveReasonEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);
}
