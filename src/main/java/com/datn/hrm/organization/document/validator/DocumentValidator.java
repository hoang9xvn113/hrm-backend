package com.datn.hrm.organization.document.validator;

import com.datn.hrm.common.exception.model.BadRequestException;
import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.organization.document.dto.Document;
import com.datn.hrm.organization.document.entity.DocumentEntity;
import com.datn.hrm.organization.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentValidator {

    public void validateExist(Long parentId) {

        if (ValidatorUtils.isNull(parentId)) return;

        Optional<DocumentEntity> entity = repository.findById(parentId);

        if (entity.isEmpty()) throw new EntityNotFoundException("Tài liệu không tồn tại");
    }

    public void validateSize(Long size) {

        int maxSize = 10 * 1024 * 1024;

        if (size > maxSize) {
            throw new BadRequestException("Dung lượng tệp không được quá 10 mb");
        }
    }

    public void validateDuplicateForAdd(String name, Long parentId) {
        Optional<DocumentEntity> entity = repository.findByNameAndParentId(name, parentId);

        if (entity.isPresent()) throw new DuplicateException("Tên tài liệu không được trùng");
    }

    public void validateDuplicateForUpdate(long id, Document dto) {
        Optional<DocumentEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id)
            throw new DuplicateException("Tên tài liệu không được trùng");
    }

    @Autowired
    DocumentRepository repository;
}
