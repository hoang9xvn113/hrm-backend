package com.datn.hrm.organization.job.validator;

import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.organization.job.dto.JobTitle;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import com.datn.hrm.organization.job.repository.JobTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobTitleValidator implements IValidator<JobTitle> {

    @Override
    public void validateForPost(JobTitle dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, JobTitle dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<JobTitleEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Chức vụ không tồn tại");
        }
    }

    private void validateRequiredFields(JobTitle dto) {

    }

    private void validateExists(JobTitle dto) {

    }

    private void validateInvalidFields(JobTitle dto) {

    }

    private void validateDuplicateForAdd(JobTitle dto) {
        Optional<JobTitleEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Chức vụ không được trùng");
    }

    private void validateDuplicateForUpdate(long id, JobTitle dto) {
        Optional<JobTitleEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id) throw new DuplicateException("Chức vụ không được trùng");
    }

    @Autowired
    JobTitleRepository repository;
}
