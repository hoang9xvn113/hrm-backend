package com.datn.hrm.notification.repository;

import com.datn.hrm.notification.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    Optional<NotificationEntity> findByName(String name);

    Page<NotificationEntity> searchAllByNameContainingIgnoreCase(String search, Pageable pageable);

    Page<NotificationEntity> searchAllByReceiverIdOrderByCreateDateDesc(Long receiverId, Pageable pageable);
}
