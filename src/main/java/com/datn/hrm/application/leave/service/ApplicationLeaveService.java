package com.datn.hrm.application.leave.service;

import com.datn.hrm.application.leave.dto.ApplicationLeave;
import com.datn.hrm.application.leave.entity.ApplicationLeaveEntity;
import com.datn.hrm.application.leave.mapper.ApplicationLeaveMapper;
import com.datn.hrm.application.leave.repository.ApplicationLeaveRepository;
import com.datn.hrm.common.service.EmailService;
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
public class ApplicationLeaveService implements IService<ApplicationLeave> {

    @Override
    public Page<ApplicationLeave> getPage(String search, Pageable pageable, String sort, String filter) {

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
    public ApplicationLeave getObject(long id) {

        return mapper.mapDtoFromEntity(repository.findById(id).orElseThrow());
    }

    @Override
    public ApplicationLeave postObject(ApplicationLeave dto) {

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(dto))
        );
    }

    public ApplicationLeave putObject(long id, long creatorId, String creatorName, String status) throws FirebaseMessagingException {

        ApplicationLeaveEntity entity = repository.findById(id).orElseThrow();

        String description = "";

        if (status.equalsIgnoreCase(EStatus.APPROVED.getValue())) {

            description = "Bạn đã duyệt một đơn xin nghỉ nhấp vào đường dẫn để xem";

        } else if (status.equalsIgnoreCase(EStatus.PENDING.getValue())) {

            if (entity.getStatus().equalsIgnoreCase(EStatus.DRAFT.getValue())) {
                description = "Bạn có một đơn xin nghỉ cần duyệt xin vui lòng nhấp vào đường dẫn để chuyển hướng";
            } else {
                description = "Bạn đã hoàn duyệt một đơn xin nghỉ nhấp vào đường dẫn để xem";
            }
        }

        String title = creatorName + " đã gửi cho bạn một thông báo";


        Optional<AccountEntity> accountEntity = accountRepository.findByEmployeeEntity(entity.getReviewer());

        if (accountEntity.isPresent()) {
            notificationService.addNotification(
                    entity.getId(),
                    "leave",
                    title,
                    description,
                    creatorId,
                    accountEntity.get().getId()
            );
        }

        emailService.sendEmail("bestofmodskin@gmail.com", "Hoang Text", "Hoang subject");

        entity.setStatus(status);

        return mapper.mapDtoFromEntity(repository.save(entity));
    }

    @Override
    public ApplicationLeave putObject(long id, ApplicationLeave dto) {

        ApplicationLeaveEntity entity = repository.findById(id).orElseThrow();

        return mapper.mapDtoFromEntity(repository.save(mapper.mapEntityFromDto(entity, dto)));
    }

    @Override
    public void deleteObject(long id) {

        repository.deleteById(id);
    }

    @Autowired
    ApplicationLeaveRepository repository;

    @Autowired
    ApplicationLeaveMapper mapper;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EmailService emailService;
}
