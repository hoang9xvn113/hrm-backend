package com.datn.hrm.application.leave.service;

import com.datn.hrm.application.leave.dto.ApplicationLeave;
import com.datn.hrm.application.leave.entity.ApplicationLeaveEntity;
import com.datn.hrm.application.leave.mapper.ApplicationLeaveMapper;
import com.datn.hrm.application.leave.repository.ApplicationLeaveRepository;
import com.datn.hrm.common.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ApplicationLeaveService implements IService<ApplicationLeave> {

    @Override
    public Page<ApplicationLeave> getPage(String search, Pageable pageable, String sort, String filter) {

        return mapper.mapDtoEntityFromEntityPage(
                repository.findAll(pageable)
        );
    }

    @Override
    public ApplicationLeave getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public ApplicationLeave postObject(ApplicationLeave dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    public ApplicationLeave putObject(long id, String status) {

        ApplicationLeaveEntity entity = repository.findById(id).orElseThrow();

        entity.setStatus(status);

        return mapper.mapDtoFromEntity(repository.save(entity));
    }

    @Override
    public ApplicationLeave putObject(long id, ApplicationLeave dto) {

        ApplicationLeaveEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    ApplicationLeaveRepository repository;

    @Autowired
    ApplicationLeaveMapper mapper;
}
