package com.datn.hrm.personnel.account.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.personnel.account.dto.Account;
import com.datn.hrm.personnel.account.entity.AccountEntity;
import com.datn.hrm.personnel.account.mapper.AccountMapper;
import com.datn.hrm.personnel.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IService<Account> {

    @Override
    public Page<Account> getPage(String search, Pageable pageable, String sort, String filter) {

        Page<AccountEntity> entities = repository.findAll(pageable);

        return mapper.mapDtoEntityFromEntityPage(
                entities
        );
    }

    @Override
    public Account getObject(long id) {
        return mapper.mapDtoFromEntity(repository.findById(id).get());
    }

    public Account getObject(String username) {

        return mapper.mapDtoFromEntity(repository.findByUsername(username).get());
    }

    @Override
    public Account postObject(Account dto) {

        return mapper.mapDtoFromEntity(
                repository.saveAndFlush(
                        mapper.mapEntityFromDto(dto)
                )
        );
    }

    @Override
    public Account putObject(long id, Account dto) {

        AccountEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    AccountRepository repository;

    @Autowired
    AccountMapper mapper;
}
