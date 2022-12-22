package com.datn.hrm.setting.personnel.allowance.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.setting.personnel.allowance.dto.Allowance;
import com.datn.hrm.setting.personnel.allowance.entity.AllowanceEntity;
import com.datn.hrm.setting.personnel.allowance.mapper.AllowanceMapper;
import com.datn.hrm.setting.personnel.allowance.repository.AllowanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AllowanceService implements IService<Allowance> {

    @Override
    public Page<Allowance> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCase(search, pageable)
        );
    }

    @Override
    public Allowance getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public Allowance postObject(Allowance dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public Allowance putObject(long id, Allowance dto) {

        AllowanceEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto))
        );
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    AllowanceRepository repository;

    @Autowired
    AllowanceMapper mapper;
}
