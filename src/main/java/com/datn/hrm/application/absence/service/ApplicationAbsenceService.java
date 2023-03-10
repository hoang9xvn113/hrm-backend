package com.datn.hrm.application.absence.service;

import com.datn.hrm.application.absence.dto.ApplicationAbsence;
import com.datn.hrm.application.absence.entity.ApplicationAbsenceEntity;
import com.datn.hrm.application.absence.mapper.ApplicationAbsenceMapper;
import com.datn.hrm.application.absence.repository.ApplicationAbsenceRepository;
import com.datn.hrm.common.service.IService;
import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.notification.service.NotificationService;
import com.datn.hrm.personnel.account.entity.AccountEntity;
import com.datn.hrm.personnel.account.repository.AccountRepository;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationAbsenceService implements IService<ApplicationAbsence> {

    @Override
    public Page<ApplicationAbsence> getPage(String search, Pageable pageable, String sort, String filter) {

        if (ValidatorUtils.isNotNull(filter)) {

            EmployeeEntity employeeEntity = employeeRepository.getReferenceById(Long.parseLong(filter));

            return mapper.mapDtoEntityFromEntityPage(
                    repository.getAllByEmployee(employeeEntity, pageable)
            );
        } else {

            return mapper.mapDtoEntityFromEntityPage(
                    repository.findAll(pageable)
            );
        }
    }

    @Override
    public ApplicationAbsence getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public ApplicationAbsence postObject(ApplicationAbsence dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public ApplicationAbsence putObject(long id, ApplicationAbsence dto) {

        ApplicationAbsenceEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    public ApplicationAbsence putObject(long id, long creatorId, String creatorName, String status) throws FirebaseMessagingException {

        ApplicationAbsenceEntity entity = repository.findById(id).orElseThrow();

        String description = "";

        if (status.equalsIgnoreCase(EStatus.APPROVED.getValue())) {

            description = "B???n ???? duy???t m???t ????n v???ng m???t nh???p v??o ???????ng d???n ????? xem";

        } else if (status.equalsIgnoreCase(EStatus.PENDING.getValue())) {

            if (entity.getStatus().equalsIgnoreCase(EStatus.DRAFT.getValue())) {
                description = "B???n c?? m???t ????n v???ng m???t c???n duy???t xin vui l??ng nh???p v??o ???????ng d???n ????? chuy???n h?????ng";
            } else {
                description = "B???n ???? ho??n duy???t m???t ????n v???ng m???t nh???p v??o ???????ng d???n ????? xem";
            }
        }

        String title = creatorName + " ???? g???i cho b???n m???t th??ng b??o";

        Optional<AccountEntity> accountEntity = accountRepository.findByEmployeeEntity(entity.getReviewer());

        if (ValidatorUtils.isNotNull(accountEntity)) {
            notificationService.addNotification(
                    entity.getId(),
                    "absence",
                    title,
                    description,
                    creatorId,
                    accountEntity.get().getId()
            );
        }

        entity.setStatus(status);

        return mapper.mapDtoFromEntity(repository.save(entity));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    ApplicationAbsenceRepository repository;

    @Autowired
    ApplicationAbsenceMapper mapper;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AccountRepository accountRepository;
}
