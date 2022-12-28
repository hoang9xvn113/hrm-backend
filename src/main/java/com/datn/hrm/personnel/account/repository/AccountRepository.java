package com.datn.hrm.personnel.account.repository;

import com.datn.hrm.personnel.account.entity.AccountEntity;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUsername(String username);

    Optional<AccountEntity> findByEmployeeEntity(EmployeeEntity employeeEntity);

    Optional<AccountEntity> getAccountEntitiesByUsernameAndPassword(String username, String password);

}
