package com.datn.hrm.application.absence.validator;

import com.datn.hrm.application.absence.dto.ApplicationAbsence;
import com.datn.hrm.application.absence.entity.ApplicationAbsenceEntity;
import com.datn.hrm.application.absence.repository.ApplicationAbsenceRepository;
import com.datn.hrm.application.leave.entity.ApplicationLeaveEntity;
import com.datn.hrm.application.utils.ApplicationUtils;
import com.datn.hrm.common.exception.model.BadRequestException;
import com.datn.hrm.common.exception.model.DuplicateException;
import com.datn.hrm.common.exception.model.EntityNotFoundException;
import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.common.validator.IValidator;
import com.datn.hrm.setting.application.absence.entity.AbsenceReasonEntity;
import com.datn.hrm.setting.application.absence.repository.AbsenceReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ApplicationAbsenceValidator implements IValidator<ApplicationAbsence> {

    @Override
    public void validateForPost(ApplicationAbsence dto) {

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

//        validateDuplicateForAdd(dto);
    }

    @Override
    public void validateForPut(long id, ApplicationAbsence dto) {

        validateForExist(id);

        validateRequiredFields(dto);

        validateExists(dto);

        validateInvalidFields(dto);

//        validateDuplicateForUpdate(id, dto);
    }

    public void validateForDelete(long id) {

        validateForExist(id);

        ApplicationAbsenceEntity entity = repository.getReferenceById(id);

        if (entity.getStatus().equalsIgnoreCase(EStatus.APPROVED.getValue())) {
            throw new BadRequestException("Đơn từ này không thể xóa");
        }
    }

    @Override
    public void validateForExist(long id) {

        Optional<ApplicationAbsenceEntity> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("ApplicationAbsence không tồn tại");
        }
    }

    private void validateRequiredFields(ApplicationAbsence dto) {

    }

    private void validateExists(ApplicationAbsence dto) {

    }

    private void validateInvalidFields(ApplicationAbsence dto) {

        if (dto.getStartTime().after(dto.getEndTime())) {
            throw new BadRequestException("Đến giờ không được nhỏ hơn từ giờ");
        }

        AbsenceReasonEntity absenceReasonEntity = absenceReasonRepository.getReferenceById(dto.getReason().getId());

        int max = absenceReasonEntity.getMax();

        String unit = absenceReasonEntity.getUnit();

        Date fromDate;
        Date toDate;
        LocalDate currentLocalDate = LocalDate.now();
        LocalDate date = dto.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

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

        List<ApplicationAbsenceEntity> list = repository.
                getAllByDateBetweenAndReason(fromDate, toDate, absenceReasonEntity);

        atomicReference.set((double) list.size() + 1);

        if (atomicReference.get() > max) {
            throw new BadRequestException(
                    "Bạn không được vắng mặt quá " + max + " Ngày/" + ApplicationUtils.convertUnit(unit) + " với lý do " + absenceReasonEntity.getName());
        }
    }

    private void validateDuplicateForAdd(ApplicationAbsence dto) {
        Optional<ApplicationAbsenceEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent()) throw new DuplicateException("ApplicationAbsence không được trùng");
    }

    private void validateDuplicateForUpdate(long id, ApplicationAbsence dto) {
        Optional<ApplicationAbsenceEntity> entity = repository.findByName(dto.getName().trim());

        if (entity.isPresent() && entity.get().getId() != id)
            throw new DuplicateException("ApplicationAbsence không được trùng");
    }

    @Autowired
    ApplicationAbsenceRepository repository;

    @Autowired
    AbsenceReasonRepository absenceReasonRepository;
}
