package com.datn.hrm.setting.application.leave.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.setting.application.leave.dto.LeaveReason;
import com.datn.hrm.setting.application.leave.entity.LeaveReasonEntity;
import com.datn.hrm.setting.application.leave.mapper.LeaveReasonMapper;
import com.datn.hrm.setting.application.leave.repository.LeaveReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LeaveReasonService implements IService<LeaveReason> {

    @Override
    public Page<LeaveReason> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.searchAllByNameContainingIgnoreCase(search, pageable)
        );
    }

    @Override
    public LeaveReason getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public LeaveReason postObject(LeaveReason dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public LeaveReason putObject(long id, LeaveReason dto) {

        LeaveReasonEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    LeaveReasonRepository repository;

    @Autowired
    LeaveReasonMapper mapper;
}
