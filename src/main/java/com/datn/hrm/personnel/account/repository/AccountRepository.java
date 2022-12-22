package com.datn.hrm.personnel.account.repository;

import com.datn.hrm.personnel.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUsername(String username);

    Optional<AccountEntity> getAccountEntitiesByUsernameAndPassword(String username, String password);

}
