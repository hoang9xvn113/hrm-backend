package com.datn.hrm.personnel.contract.listener;

import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.personnel.career.service.EmployeeCareerService;
import com.datn.hrm.personnel.contract.entity.ContractEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Service
@RequiredArgsConstructor
public class ContractListener {

    @Autowired
    private EmployeeCareerService employeeCareerService;
    private Log log = LogFactory.getLog(ContractListener.class);
    @PostUpdate
    public void onAfterUpdate(ContractEntity entity) {
        if (entity.getStatus().equalsIgnoreCase(EStatus.APPROVED.getValue())) {

        }
        log.info(entity.getId());

//        employeeCareerService.addObject(
//                entity.getEmployeeEntity(),
//                entity.getDepartmentEntity(),
//                entity.getJobPositionEntity(),
//                entity.getJobTitleEntity(),
//                entity.getContractTypeEntity(),
//                entity.getEffectiveDate()
//        );
    }

    @PostPersist
    void onAfterCreate(ContractEntity entity) {
    }

    @PostRemove
    void onAfterRemove(ContractEntity entity) {

    }
}
