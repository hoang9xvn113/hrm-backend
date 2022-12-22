package com.datn.hrm.dashboard.article.listener;

import com.datn.hrm.common.utils.EStatus;
import com.datn.hrm.personnel.career.service.EmployeeCareerService;
import com.datn.hrm.personnel.contract.entity.ContractEntity;
import com.datn.hrm.personnel.contract.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@Component
public class ArticleListener {

    private final Logger logger = LoggerFactory.getLogger(ArticleListener.class);

//    @PreUpdate
//    public void onPreUpdate(ContractEntity entity) {
//        entity.setModifiedDate(new Date());
//    }
//
//    @PrePersist
//    void prePersist(ContractEntity entity) {
////        entity.setCreateDate(new Date());
////        entity.setModifiedDate(new Date());
////        entity.setStatus(EStatus.PENDING.getValue());
//    }
}
