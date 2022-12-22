package com.datn.hrm.setting.application.absence.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.setting.application.absence.dto.AbsenceReason;
import com.datn.hrm.setting.application.absence.entity.AbsenceReasonEntity;
import com.datn.hrm.setting.application.absence.mapper.AbsenceReasonMapper;
import com.datn.hrm.setting.application.absence.repository.AbsenceReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AbsenceReasonService implements IService<AbsenceReason> {

    @Override
    public Page<AbsenceReason> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCase(search, pageable)
        );
    }

    @Override
    public AbsenceReason getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public AbsenceReason postObject(AbsenceReason dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public AbsenceReason putObject(long id, AbsenceReason dto) {

        AbsenceReasonEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    AbsenceReasonRepository repository;

    @Autowired
    AbsenceReasonMapper mapper;
}
