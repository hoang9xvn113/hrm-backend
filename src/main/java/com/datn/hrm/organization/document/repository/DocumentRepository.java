package com.datn.hrm.organization.document.repository;

import com.datn.hrm.organization.document.entity.DocumentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    Optional<DocumentEntity> findByName(String name);

    Page<DocumentEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);

    Page<DocumentEntity> searchAllByNameContainingIgnoreCaseAndParentId(String search, Long parentId, Pageable pageable);

    Optional<DocumentEntity> findByNameAndParentId(String name, Long parentId);

    List<DocumentEntity> findAllByParentId(Long parentId);
}
