package com.datn.hrm.setting.personnel.contract.type.mapper;

import com.datn.hrm.common.mapper.IMapper;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.setting.personnel.allowance.dto.Allowance;
import com.datn.hrm.setting.personnel.allowance.entity.AllowanceEntity;
import com.datn.hrm.setting.personnel.allowance.mapper.AllowanceMapper;
import com.datn.hrm.setting.personnel.category.entity.CategoryEntity;
import com.datn.hrm.setting.personnel.category.repository.CategoryRepository;
import com.datn.hrm.setting.personnel.contract.type.dto.ContractType;
import com.datn.hrm.setting.personnel.contract.type.entity.ContractTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractTypeMapper implements IMapper<ContractType, ContractTypeEntity> {

    @Override
    public ContractType mapDtoFromEntity(ContractTypeEntity entity) {

        ContractType dto = new ContractType();

        if (ValidatorUtils.isNull(entity)) return dto;

        dto.setId(entity.getId());

        dto.setName(entity.getName());
        dto.setDuration(entity.getDuration());
        dto.setUnit(entity.getUnit());

        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(entity.getCategoryId());
        categoryEntity.ifPresent((data) -> {
            dto.setCategoryId(data.getId());
            dto.setCategoryName(data.getName());
        });

        if (ValidatorUtils.isNotNull(dto.getAllowances())) {
            dto.setAllowances(
                    entity.getAllowanceEntities().
                            stream().
                            map((allowanceEntity -> allowanceMapper.mapDtoFromEntity(allowanceEntity))).
                            collect(Collectors.toList()).
                            toArray(new Allowance[]{})
            );
        }

        dto.setCreatorId(entity.getCreatorId());
        dto.setCreateDate(entity.getCreateDate());
        dto.setModifiedDate(entity.getModifiedDate());

        return dto;
    }

    @Override
    public ContractTypeEntity mapEntityFromDto(ContractType dto) {

        ContractTypeEntity entity = new ContractTypeEntity();

        mapEntityFromDto(entity, dto);

        return entity;
    }

    @Override
    public ContractTypeEntity mapEntityFromDto(ContractTypeEntity entity, ContractType dto) {

        entity.setName(dto.getName().trim());
        entity.setName(dto.getName());
        entity.setDuration(dto.getDuration());
        entity.setUnit(dto.getUnit());
        entity.setCategoryId(dto.getCategoryId());

        if (ValidatorUtils.isNotNull(dto.getAllowances())) {
            entity.setAllowanceEntities(
                    Arrays.stream(dto.getAllowances()).map((item) -> {
                        AllowanceEntity allowanceEntity = new AllowanceEntity();

                        allowanceEntity.setId(item.getId());

                        return allowanceEntity;
                    }).collect(Collectors.toList())
            );
        }

        return entity;
    }

    @Override
    public Page<ContractType> mapDtoEntityFromEntityPage(Page<ContractTypeEntity> entities) {

        return entities.map(this::mapDtoFromEntity);
    }

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AllowanceMapper allowanceMapper;
}
