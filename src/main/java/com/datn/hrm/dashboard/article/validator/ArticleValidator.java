package com.datn.hrm.dashboard.article.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.dashboard.article.dto.Article;
import com.datn.hrm.dashboard.article.entity.ArticleEntity;
import com.datn.hrm.dashboard.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleValidator implements IValidator<Article> {

    @Override
    public void validateForPost(Article dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, Article dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<ArticleEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Article không tồn tại");
        }
    }

    private void validateRequiredFields(Article dto) {

    }

    private void validateExists(Article dto) {

    }

    private void validateInvalidFields(Article dto) {

    }

    private void validateDuplicateForAdd(Article dto) {
        Optional<ArticleEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Article không được trùng");
    }

    private void validateDuplicateForUpdate(long id, Article dto) {
        Optional<ArticleEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id) throw new DuplicateException("Article không được trùng");
    }

    @Autowired
    ArticleRepository repository;
}
