package com.datn.hrm.application.leave.validator;

import com.datn.hrm.application.leave.dto.ApplicationLeave;
import com.datn.hrm.application.leave.entity.ApplicationLeaveEntity;
import com.datn.hrm.application.leave.mapper.ApplicationLeaveMapper;
import com.datn.hrm.application.leave.repository.ApplicationLeaveRepository;
import com.datn.hrm.application.utils.ApplicationUtils;
import com.datn.hrm.common.exception.model.BadRequestException;
import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.setting.application.leave.entity.LeaveReasonEntity;
import com.datn.hrm.setting.application.leave.repository.LeaveReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ApplicationLeaveValidator implements IValidator<ApplicationLeave> {

    @Override
    public void validateForPost(ApplicationLeave dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);
//
//        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, ApplicationLeave dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

//        validateDuplicateForUpdate(id, dto);
    }

    @Override
    public void validateForExist(long id) {

        Optional<ApplicationLeaveEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("ApplicationLeave không tồn tại");
        }
    }

    private void validateRequiredFields(ApplicationLeave dto) {

    }

    private void validateExists(ApplicationLeave dto) {

    }

    private void validateInvalidFields(ApplicationLeave dto) {

        if (dto.getStartDate().after(dto.getEndDate())) {
            throw new BadRequestException("Ngày bắt đầu không được nhỏ hơn ngày kết thúc");
        }

        LeaveReasonEntity leaveReasonEntity = leaveReasonRepository.getReferenceById(dto.getReason().getId());

        int max = leaveReasonEntity.getMax();

        String unit = leaveReasonEntity.getUnit();

        Date fromDate;
        Date toDate;
        LocalDate currentLocalDate = LocalDate.now();
        LocalDate date = dto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        switch (unit) {
            case "week":
                int current = currentLocalDate.getDayOfWeek().getValue();
                fromDate = Date.from(currentLocalDate.
                        minusDays(current - 1).
                        atStartOfDay(ZoneId.systemDefault()).
                        toInstant());
                toDate = Date.from(currentLocalDate.
                        plusDays(8 - current).
                        atStartOfDay(ZoneId.systemDefault()).
                        minusSeconds(1).
                        toInstant()
                );
                break;
            case "month":
                fromDate = Date.from(
                        LocalDate.of(date.getYear(), date.getMonthValue(), 1).
                                atStartOfDay(ZoneId.systemDefault()).
                                toInstant()
                );
                toDate = Date.from(
                        LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth()).
                                plusDays(1).
                                atStartOfDay(ZoneId.systemDefault()).
                                minusSeconds(1).
                                toInstant()
                );
                break;
            default:
                fromDate = Date.from(
                        LocalDate.of(date.getYear(), 1, 1).
                                atStartOfDay(ZoneId.systemDefault()).
                                toInstant()
                );
                toDate = Date.from(
                        LocalDate.of(date.getYear(), 12, 31).
                                plusDays(1).
                                atStartOfDay(ZoneId.systemDefault()).
                                minusSeconds(1).
                                toInstant()
                );
                break;
        }

        AtomicReference<Double> atomicReference = new AtomicReference<>(0D);

        List<ApplicationLeaveEntity> list = repository.
                getAllByStartDateBetweenAndReason(fromDate, toDate, leaveReasonEntity);

        list.forEach(item -> {
            atomicReference.set(item.getDateCount());
        });

        atomicReference.set(atomicReference.get() + mapper.calculateDateCount(
                dto.getStartShift(),
                dto.getStartDate(),
                dto.getEndShift(),
                dto.getEndDate()
        ));

        if (atomicReference.get() > max) {
            throw new BadRequestException(
                    "Bạn không được nghỉ quá " + max + " Ngày/" + ApplicationUtils.convertUnit(unit) + " với lý do " + leaveReasonEntity.getName());
        }
    }

    private void validateDuplicateForAdd(ApplicationLeave dto) {
        Optional<ApplicationLeaveEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("ApplicationLeave không được trùng");
    }

    private void validateDuplicateForUpdate(long id, ApplicationLeave dto) {
        Optional<ApplicationLeaveEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id)
            throw new DuplicateException("ApplicationLeave không được trùng");
    }

    @Autowired
    ApplicationLeaveRepository repository;

    @Autowired
    LeaveReasonRepository leaveReasonRepository;

    @Autowired
    ApplicationLeaveMapper mapper;
}
