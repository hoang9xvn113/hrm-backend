package com.datn.hrm.organization.job.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.organization.job.dto.JobTitle;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class JobTitleMapper implements IMapper<JobTitle, JobTitleEntity> {

    @Override
    public JobTitle mapDtoFromEntity(JobTitleEntity entity) {

        JobTitle dto = new JobTitle();

        dto.setId(entity.getId());

        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public JobTitleEntity mapEntityFromDto(JobTitle dto) {

        JobTitleEntity entity = new JobTitleEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public JobTitleEntity mapEntityFromDto(JobTitleEntity entity, JobTitle dto) {

        entity.setName(dto.getName().trim());
        entity.setDescription(dto.getDescription());

        return entity;
    }

    @Override
    public Page<JobTitle> mapDtoEntityFromEntityPage(Page<JobTitleEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }
}
