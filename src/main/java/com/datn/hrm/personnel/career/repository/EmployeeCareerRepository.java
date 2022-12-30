package com.datn.hrm.personnel.career.repository;

import com.datn.hrm.personnel.career.entity.EmployeeCareerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeCareerRepository extends JpaRepository<EmployeeCareerEntity, Long> {

    Page<EmployeeCareerEntity> getAllByEmployeeIdOrderByModifiedDateDesc(Long employeeId, Pageable pageable);

    @Query(value = "select e from EmployeeCareerEntity e " +
            "where e.employeeId = :employeeId and e.effectiveDate <= :date " +
            "order by e.effectiveDate desc "
    )
    List<EmployeeCareerEntity> getEmployeeCareers(long employeeId, Date date);

    EmployeeCareerEntity getEmployeeCareerEntityByPkIdAndStatus(Long pkId, String status);
}
