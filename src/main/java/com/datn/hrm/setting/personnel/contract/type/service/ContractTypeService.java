package com.datn.hrm.setting.personnel.contract.type.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.setting.personnel.contract.type.repository.ContractTypeRepository;
import com.datn.hrm.setting.personnel.contract.type.dto.ContractType;
import com.datn.hrm.setting.personnel.contract.type.entity.ContractTypeEntity;
import com.datn.hrm.setting.personnel.contract.type.mapper.ContractTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContractTypeService implements IService<ContractType> {

    @Override
    public Page<ContractType> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCase(search, pageable)
        );
    }

    @Override
    public ContractType getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public ContractType postObject(ContractType dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public ContractType putObject(long id, ContractType dto) {

        ContractTypeEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto))
        );
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    ContractTypeRepository repository;

    @Autowired
    ContractTypeMapper mapper;
}
