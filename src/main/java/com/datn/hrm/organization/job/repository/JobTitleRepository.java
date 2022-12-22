package com.datn.hrm.organization.job.repository;

import com.datn.hrm.organization.job.entity.JobTitleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitleEntity, Long> {

    Optional<JobTitleEntity> findByName(String name);

    Page<JobTitleEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);
}
