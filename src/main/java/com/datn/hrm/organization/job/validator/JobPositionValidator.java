package com.datn.hrm.organization.job.validator;

import com.datn.hrm.common.exception.model.BadRequestException;
import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.utils.GetterUtils;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.organization.job.dto.JobPosition;
import com.datn.hrm.organization.job.entity.JobPositionEntity;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import com.datn.hrm.organization.job.repository.JobPositionRepository;
import com.datn.hrm.organization.job.repository.JobTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobPositionValidator implements IValidator<JobPosition> {

    @Override
    public void validateForPost(JobPosition dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, JobPosition dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<JobPositionEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Vị trí công việc không tồn tại");
        }
    }

    private void validateRequiredFields(JobPosition dto) {

    }

    private void validateExists(JobPosition dto) {

        if (ValidatorUtils.isNotNull(dto.getJobTitleId())) {
            Optional<JobTitleEntity> jobTitleEntity = jobTitleRepository.findById(dto.getJobTitleId());

            if (jobTitleEntity.isEmpty()) throw new EntityNotFoundException(
                    "Chức vụ không tồn tại"
            );
        }
    }

    private void validateInvalidFields(JobPosition dto) {
        if (GetterUtils.getDouble(dto.getMaxSalary()) < GetterUtils.getDouble(dto.getMinSalary())) {
            throw new BadRequestException("Lương tối đa phải lớn hơn lương tối thiểu");
        }
    }

    private void validateDuplicateForAdd(JobPosition dto) {
        Optional<JobPositionEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("Vị trí công việc không được trùng");
    }

    private void validateDuplicateForUpdate(long id, JobPosition dto) {
        Optional<JobPositionEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id) throw new DuplicateException("Vị trí công việc không được trùng");
    }

    @Autowired
    JobPositionRepository repository;

    @Autowired
    JobTitleRepository jobTitleRepository;
}
