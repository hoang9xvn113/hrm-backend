package com.datn.hrm.personnel.contract.repository;

import com.datn.hrm.personnel.contract.entity.ContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Long> {

//    Optional<ContractEntity> findByName(String name);

    Optional<ContractEntity> findByCode(String code);

    Page<ContractEntity> searchAllByCodeContainingIgnoreCase(String search, Pageable pageable);
}
