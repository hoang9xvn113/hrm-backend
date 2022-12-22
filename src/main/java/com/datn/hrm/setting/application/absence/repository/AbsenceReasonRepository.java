package com.datn.hrm.setting.application.absence.repository;

import com.datn.hrm.setting.application.absence.entity.AbsenceReasonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbsenceReasonRepository extends JpaRepository<AbsenceReasonEntity, Long> {

    Optional<AbsenceReasonEntity> findByName(String name);

    Page<AbsenceReasonEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);
}
