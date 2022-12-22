package com.datn.hrm.application.absence.service;

import com.datn.hrm.application.absence.dto.ApplicationAbsence;
import com.datn.hrm.application.absence.entity.ApplicationAbsenceEntity;
import com.datn.hrm.application.absence.mapper.ApplicationAbsenceMapper;
import com.datn.hrm.application.absence.repository.ApplicationAbsenceRepository;
import com.datn.hrm.common.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ApplicationAbsenceService implements IService<ApplicationAbsence> {

    @Override
    public Page<ApplicationAbsence> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.findAll(pageable)
        );
    }

    @Override
    public ApplicationAbsence getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public ApplicationAbsence postObject(ApplicationAbsence dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public ApplicationAbsence putObject(long id, ApplicationAbsence dto) {

        ApplicationAbsenceEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    public ApplicationAbsence putObject(long id, String status) {

        ApplicationAbsenceEntity entity = repository.findById(id).orElseThrow();

        entity.setStatus(status);

        return mapper.mapDtoFromEntity(repository.save(entity));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    ApplicationAbsenceRepository repository;

    @Autowired
    ApplicationAbsenceMapper mapper;
}
