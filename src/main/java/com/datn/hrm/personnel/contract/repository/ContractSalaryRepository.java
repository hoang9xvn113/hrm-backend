package com.datn.hrm.personnel.contract.repository;

import com.datn.hrm.personnel.contract.entity.ContractSalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractSalaryRepository extends JpaRepository<ContractSalaryEntity, Long> {

    List<ContractSalaryEntity> findAllByContractId(long contractId);

    void deleteAllByContractId(long contractId);
}
