package com.datn.hrm.application.resignation.service;

import com.datn.hrm.application.resignation.dto.ApplicationResignation;
import com.datn.hrm.application.resignation.entity.ApplicationResignationEntity;
import com.datn.hrm.application.resignation.mapper.ApplicationResignationMapper;
import com.datn.hrm.application.resignation.repository.ApplicationResignationRepository;
import com.datn.hrm.common.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ApplicationResignationService implements IService<ApplicationResignation> {

    @Override
    public Page<ApplicationResignation> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.findAll(pageable)
        );
    }

    @Override
    public ApplicationResignation getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public ApplicationResignation postObject(ApplicationResignation dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public ApplicationResignation putObject(long id, ApplicationResignation dto) {

        ApplicationResignationEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    public ApplicationResignation putObject(long id, String status) {

        ApplicationResignationEntity entity = repository.findById(id).orElseThrow();

        entity.setStatus(status);

        return mapper.mapDtoFromEntity(repository.save(entity));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    ApplicationResignationRepository repository;

    @Autowired
    ApplicationResignationMapper mapper;
}
