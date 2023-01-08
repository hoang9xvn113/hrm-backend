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

            description = "Bạn đã duyệt một đơn vắng mặt nhấp vào đường dẫn để xem";

        } else if (status.equalsIgnoreCase(EStatus.PENDING.getValue())) {

            if (entity.getStatus().equalsIgnoreCase(EStatus.DRAFT.getValue())) {
                description = "Bạn có một đơn vắng mặt cần duyệt xin vui lòng nhấp vào đường dẫn để chuyển hướng";
            } else {
                description = "Bạn đã hoàn duyệt một đơn vắng mặt nhấp vào đường dẫn để xem";
            }
        }

        String title = creatorName + " đã gửi cho bạn một thông báo";

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
