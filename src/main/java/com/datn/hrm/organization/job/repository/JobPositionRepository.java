package com.datn.hrm.organization.job.repository;

import com.datn.hrm.organization.job.entity.JobPositionEntity;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPositionEntity, Long> {

    Optional<JobPositionEntity> findByName(String name);

    Page<JobPositionEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);
}
