package com.datn.hrm.setting.application.resignation.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.setting.application.resignation.dto.ResignationReason;
import com.datn.hrm.setting.application.resignation.entity.ResignationReasonEntity;
import com.datn.hrm.setting.application.resignation.mapper.ResignationReasonMapper;
import com.datn.hrm.setting.application.resignation.repository.ResignationReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ResignationReasonService implements IService<ResignationReason> {

    @Override
    public Page<ResignationReason> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCase(search, pageable)
        );
    }

    @Override
    public ResignationReason getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public ResignationReason postObject(ResignationReason dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public ResignationReason putObject(long id, ResignationReason dto) {

        ResignationReasonEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    ResignationReasonRepository repository;

    @Autowired
    ResignationReasonMapper mapper;
}
