package com.datn.hrm.organization.job.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.organization.job.dto.JobPosition;
import com.datn.hrm.organization.job.entity.JobPositionEntity;
import com.datn.hrm.organization.job.mapper.JobPositionMapper;
import com.datn.hrm.organization.job.repository.JobPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobPositionService implements IService<JobPosition> {

    @Override
    public Page<JobPosition> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCase(search, pageable)
        );
    }

    @Override
    public JobPosition getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public JobPosition postObject(JobPosition dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public JobPosition putObject(long id, JobPosition dto) {

        JobPositionEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto))
        );
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    JobPositionRepository repository;

    @Autowired
    JobPositionMapper mapper;
}
