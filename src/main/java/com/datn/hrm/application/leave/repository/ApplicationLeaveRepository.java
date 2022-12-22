package com.datn.hrm.application.leave.repository;

import com.datn.hrm.application.leave.entity.ApplicationLeaveEntity;
import com.datn.hrm.setting.application.leave.entity.LeaveReasonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationLeaveRepository extends JpaRepository<ApplicationLeaveEntity, Long> {

    Optional<ApplicationLeaveEntity> findByName(String name);

    Page<ApplicationLeaveEntity> searchAllByEmployee_Name(String search, Pageable pageable);

    List<ApplicationLeaveEntity> getAllByStartDateBetweenAndReason(Date startDate, Date endDate, LeaveReasonEntity leaveReasonEntity);

//    Page<ApplicationLeaveEntity> searchAllByNameContainingIgnoreCase(Pageable pageable);
}
