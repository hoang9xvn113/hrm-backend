package com.datn.hrm.personnel.contract.service;

import com.datn.hrm.common.service.IService;
import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.notification.service.NotificationService;
import com.datn.hrm.personnel.account.entity.AccountEntity;
import com.datn.hrm.personnel.account.repository.AccountRepository;
import com.datn.hrm.personnel.career.repository.EmployeeCareerRepository;
import com.datn.hrm.personnel.career.service.EmployeeCareerService;
import com.datn.hrm.personnel.contract.dto.Contract;
import com.datn.hrm.personnel.contract.entity.ContractEntity;
import com.datn.hrm.personnel.contract.entity.ContractSalaryEntity;
import com.datn.hrm.personnel.contract.mapper.ContractMapper;
import com.datn.hrm.personnel.contract.repository.ContractRepository;
import com.datn.hrm.personnel.contract.repository.ContractSalaryRepository;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService implements IService<Contract> {

    @Override
    public Page<Contract> getPage(String search, Pageable pageable, String sort, String filter) {

        if (ValidatorUtils.isNull(filter)) {
            return mapper.mapDtoEntityFromEntityPage(
                    repository.searchAllByCodeContainingIgnoreCaseOrderByModifiedDateDesc(search, pageable)
            );
        } else {
            return mapper.mapDtoEntityFromEntityPage(
                    repository.
                            searchAllByEmployeeEntityAndCodeContainingIgnoreCaseOrderByModifiedDateDesc(
                                    employeeRepository.getReferenceById(Long.parseLong(filter)),
                                    search,
                                    pageable
                            )
            );
        }
    }

    @Override
    public Contract getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public Contract postObject(Contract dto) {

        ContractEntity entity = repository.save(mapper.mapEntityFromDto(dto));

        if (ValidatorUtils.isNotNull(dto.getContractSalaries())) {

            Arrays.stream(dto.getContractSalaries()).forEach(item -> {

                ContractSalaryEntity contractSalaryEntity = new ContractSalaryEntity();

                contractSalaryEntity.setContractId(entity.getId());
                contractSalaryEntity.setCategoryId(item.getCategory().getId());
                contractSalaryEntity.setMoney(item.getMoney());

                contractSalaryRepository.save(contractSalaryEntity);
            });
        }

        return mapper.mapDtoFromEntity(entity);
    }

    @Override
    public Contract putObject(long id, Contract dto) {

        ContractEntity entity = repository.findById(id).orElseThrow();

        List<ContractSalaryEntity> contractSalaryEntityList = contractSalaryRepository.
                findAllByContractId(entity.getId());

        contractSalaryEntityList.forEach(contractSalaryEntity -> {
            contractSalaryRepository.delete(contractSalaryEntity);
        });

        if (ValidatorUtils.isNotNull(dto.getContractSalaries())) {

            Arrays.stream(dto.getContractSalaries()).forEach(item -> {

                ContractSalaryEntity contractSalaryEntity = new ContractSalaryEntity();

                contractSalaryEntity.setContractId(entity.getId());
                contractSalaryEntity.setCategoryId(item.getCategory().getId());
                contractSalaryEntity.setMoney(item.getMoney());

                contractSalaryRepository.save(contractSalaryEntity);
            });
        }


        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    @Transactional(
            rollbackOn = {Exception.class}
    )
    public Contract putObject(long id, long creatorId, String creatorName, String status) throws FirebaseMessagingException {

        ContractEntity entity = repository.findById(id).orElseThrow();

        String description = "";

        if (status.equalsIgnoreCase(EStatus.APPROVED.getValue())) {
            employeeCareerService.addObject(
                    entity.getEmployeeEntity().getId(),
                    entity.getDepartmentEntity().getId(),
                    entity.getJobPositionEntity().getId(),
                    entity.getJobTitleEntity().getId(),
                    entity.getContractTypeEntity().getId(),
                    entity.getEffectiveDate(),
                    EStatus.PENDING.getValue(),
                    entity.getId()
            );

            description = "B???n ???? duy???t m???t h???p ?????ng nh???p v??o ???????ng d???n ????? xem";

        } else if (status.equalsIgnoreCase(EStatus.PENDING.getValue())) {
            employeeCareerService.deleteObjectByPkId(entity.getId(), EStatus.PENDING.getValue());

            if (entity.getStatus().equalsIgnoreCase(EStatus.DRAFT.getValue())) {
                description = "B???n c?? h???p ?????ng c???n duy???t xin vui l??ng nh???p v??o ???????ng d???n ????? chuy???n h?????ng";
            } else {
                description = "B???n ???? ho??n duy???t m???t h???p ?????ng nh???p v??o ???????ng d???n ????? xem";
            }
        }

        String title = creatorName + " ???? g???i cho b???n m???t th??ng b??o";

        Optional<AccountEntity> accountEntity = accountRepository.findByEmployeeEntity(entity.getReviewer());

        if (accountEntity.isPresent()) {
            notificationService.addNotification(
                    entity.getId(),
                    "contract",
                    title,
                    description,
                    creatorId,
                    accountEntity.get().getId()
            );
        }

        entity.setStatus(status);

        return mapper.mapDtoFromEntity(entity);
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    ContractRepository repository;

    @Autowired
    ContractMapper mapper;

    @Autowired
    EmployeeCareerRepository employeeCareerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ContractSalaryRepository contractSalaryRepository;

    @Autowired
    EmployeeCareerService employeeCareerService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AccountRepository accountRepository;
}
