package com.datn.hrm.organization.job.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.organization.job.dto.JobTitle;
import com.datn.hrm.organization.job.entity.JobTitleEntity;
import com.datn.hrm.organization.job.mapper.JobTitleMapper;
import com.datn.hrm.organization.job.repository.JobTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobTitleService implements IService<JobTitle> {

    @Override
    public Page<JobTitle> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCase(search, pageable)
        );
    }

    @Override
    public JobTitle getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public JobTitle postObject(JobTitle dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public JobTitle putObject(long id, JobTitle dto) {

        JobTitleEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto))
        );
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    JobTitleRepository repository;

    @Autowired
    JobTitleMapper mapper;
}
