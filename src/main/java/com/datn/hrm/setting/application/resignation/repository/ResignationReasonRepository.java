package com.datn.hrm.setting.application.resignation.repository;

import com.datn.hrm.setting.application.resignation.entity.ResignationReasonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResignationReasonRepository extends JpaRepository<ResignationReasonEntity, Long> {

    Optional<ResignationReasonEntity> findByName(String name);

    Page<ResignationReasonEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);
}
