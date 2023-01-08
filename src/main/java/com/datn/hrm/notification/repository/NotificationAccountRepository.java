package com.datn.hrm.notification.repository;

import com.datn.hrm.notification.entity.NotificationAccountEntity;
import com.datn.hrm.notification.entity.NotificationEntity;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationAccountRepository extends JpaRepository<NotificationAccountEntity, Long> {

    List<NotificationAccountEntity> getAllByAccountId(long accountId);

    Optional<NotificationAccountEntity> findByAccountIdAndDeviceToken(long accountId, String deviceToken);
}
