package com.datn.hrm.setting.personnel.category.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.setting.personnel.category.dto.Category;
import com.datn.hrm.setting.personnel.category.entity.CategoryEntity;
import com.datn.hrm.setting.personnel.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryValidator implements IValidator<Category> {

    @Override
    public void validateForPost(Category dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, Category dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<CategoryEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Danh mục không tồn tại");
        }
    }

    private void validateRequiredFields(Category dto) {

    }

    private void validateExists(Category dto) {

    }

    private void validateInvalidFields(Category dto) {

    }

    private void validateDuplicateForAdd(Category dto) {
        Optional<CategoryEntity> entity = repository.findByNameAndType(dto.getName().trim(), dto.getType());

        if (entity.isPresent()) throw new DuplicateException("Danh mục không được trùng");
    }

    private void validateDuplicateForUpdate(long id, Category dto) {
        Optional<CategoryEntity> entity = repository.findByNameAndType(dto.getName().trim(), dto.getType());

        if (entity.isPresent() && entity.get().getId() != id) throw new DuplicateException("Danh mục không được trùng");
    }

    @Autowired
    CategoryRepository repository;
}
