package com.datn.hrm.application.resignation.service;

import com.datn.hrm.application.resignation.dto.ApplicationResignation;
import com.datn.hrm.application.resignation.entity.ApplicationResignationEntity;
import com.datn.hrm.application.resignation.mapper.ApplicationResignationMapper;
import com.datn.hrm.application.resignation.repository.ApplicationResignationRepository;
import com.datn.hrm.common.service.IService;
import com.datn.hrm.common.utils.DefaultValue;
import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.common.validator.ValidatorUtils;
import com.datn.hrm.notification.service.NotificationService;
import com.datn.hrm.personnel.account.entity.AccountEntity;
import com.datn.hrm.personnel.account.repository.AccountRepository;
import com.datn.hrm.personnel.career.service.EmployeeCareerService;
import com.datn.hrm.personnel.employee.entity.EmployeeEntity;
import com.datn.hrm.personnel.employee.repository.EmployeeRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationResignationService implements IService<ApplicationResignation> {

    @Override
    public Page<ApplicationResignation> getPage(String search, Pageable pageable, String sort, String filter) {

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
    public ApplicationResignation getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public ApplicationResignation postObject(ApplicationResignation dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    @Override
    public ApplicationResignation putObject(long id, ApplicationResignation dto) {

        ApplicationResignationEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    public ApplicationResignation putObject(long id, long creatorId, String creatorName, String status) throws FirebaseMessagingException {

        ApplicationResignationEntity entity = repository.findById(id).orElseThrow();

        String description;

        if (status.equalsIgnoreCase(EStatus.APPROVED.getValue())) {

            employeeCareerService.addObject(
                    entity.getEmployee().getId(),
                    DefaultValue.LONG,
                    DefaultValue.LONG,
                    DefaultValue.LONG,
                    DefaultValue.LONG,
                    entity.getDate(),
                    EStatus.CANCEL.getValue(),
                    entity.getId()
            );

            description = "Bạn đã duyệt một đơn nghỉ việc nhấp vào đường dẫn để xem";
        } else {
            employeeCareerService.deleteObjectByPkId(entity.getId(), EStatus.CANCEL.getValue());

            if (entity.getStatus().equalsIgnoreCase(EStatus.DRAFT.getValue())) {
                description = "Bạn có đơn nghỉ việc cần duyệt xin vui lòng nhấp vào đường dẫn để chuyển hướng";
            } else {
                description = "Bạn đã hoàn duyệt một đơn nghỉ việc nhấp vào đường dẫn để xem";
            }
        }

        String title = creatorName + " đã gửi cho bạn một thông báo";

        Optional<AccountEntity> accountEntity = accountRepository.findByEmployeeEntity(entity.getReviewer());

        if (accountEntity.isPresent()) {
            notificationService.addNotification(
                    entity.getId(),
                    "resignation",
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
    ApplicationResignationRepository repository;

    @Autowired
    ApplicationResignationMapper mapper;

    @Autowired
    EmployeeCareerService employeeCareerService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AccountRepository accountRepository;
}
