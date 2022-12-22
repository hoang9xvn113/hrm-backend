package com.datn.hrm.setting.personnel.contract.type.repository;

import com.datn.hrm.setting.personnel.contract.type.entity.ContractTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractTypeRepository extends JpaRepository<ContractTypeEntity, Long> {

    Optional<ContractTypeEntity> findByName(String name);

    Page<ContractTypeEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);
}
