package com.datn.hrm.test.repository;

import com.datn.hrm.test.entity.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

    Optional<TestEntity> findByName(String name);

    Page<TestEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);
}
