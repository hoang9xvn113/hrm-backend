package com.datn.hrm.setting.personnel.category.repository;

import com.datn.hrm.setting.personnel.category.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByNameAndType(String name, String type);

    Page<CategoryEntity> searchAllByNameContainingIgnoreCaseAndType(String search, String type, Pageable pageable);
}
