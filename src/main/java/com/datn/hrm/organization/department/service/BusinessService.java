package com.datn.hrm.organization.department.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.organization.department.dto.Business;
import com.datn.hrm.organization.department.entity.BusinessEntity;
import com.datn.hrm.organization.department.mapper.BusinessMapper;
import com.datn.hrm.organization.department.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BusinessService implements IService<Business> {

    @Override
    public Page<Business> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.findAll(pageable)
        );
    }

    @Override
    public Business getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public Business postObject(Business dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public Business putObject(long id, Business dto) {

        BusinessEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto))
        );
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    BusinessRepository repository;

    @Autowired
    BusinessMapper mapper;
}
