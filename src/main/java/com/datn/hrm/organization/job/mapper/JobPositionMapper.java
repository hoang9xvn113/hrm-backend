package com.datn.hrm.organization.job.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.common.utils.GetterUtils;
import com.datn.hrm.organization.job.dto.JobPosition;
import com.datn.hrm.organization.job.entity.JobPositionEntity;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import com.datn.hrm.organization.job.repository.JobTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobPositionMapper implements IMapper<JobPosition, JobPositionEntity> {

    @Override
    public JobPosition mapDtoFromEntity(JobPositionEntity entity) {

        JobPosition dto = new JobPosition();

        dto.setId(entity.getId());

        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setMaxSalary(entity.getMaxSalary());
        dto.setMinSalary(entity.getMinSalary());

        Optional<JobTitleEntity> jobTitleEntity = jobTitleRepository.findById(entity.getJobTitleId());

        jobTitleEntity.ifPresent((data) -> {
            dto.setJobTitle(jobTitleMapper.mapDtoFromEntity(data));
            dto.setJobTitleId(data.getId());
        });

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public JobPositionEntity mapEntityFromDto(JobPosition dto) {

        JobPositionEntity entity = new JobPositionEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public JobPositionEntity mapEntityFromDto(JobPositionEntity entity, JobPosition dto) {

        entity.setName(dto.getName().trim());
        entity.setMaxSalary(dto.getMaxSalary());
        entity.setMinSalary(dto.getMinSalary());
        entity.setJobTitleId(GetterUtils.getLong(dto.getJobTitleId()));
        entity.setDescription(dto.getDescription());

        return entity;
    }

    @Override
    public Page<JobPosition> mapDtoEntityFromEntityPage(Page<JobPositionEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }

    @Autowired
    JobTitleRepository jobTitleRepository;
    @Autowired
    JobTitleMapper jobTitleMapper;
}
