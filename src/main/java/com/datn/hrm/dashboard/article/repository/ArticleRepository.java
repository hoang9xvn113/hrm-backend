package com.datn.hrm.dashboard.article.repository;

import com.datn.hrm.dashboard.article.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findByName(String name);

    Page<ArticleEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);

    Page<ArticleEntity> searchAllByNameContainingIgnoreCaseAndCategory(String search, String category, Pageable pageable);
}
