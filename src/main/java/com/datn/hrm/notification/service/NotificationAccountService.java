package com.datn.hrm.notification.service;

import com.datn.hrm.notification.entity.NotificationAccountEntity;
import com.datn.hrm.notification.repository.NotificationAccountRepository;
import com.google.firebase.messaging.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationAccountService {

    private final Log log = LogFactory.getLog(NotificationAccountService.class);

    public void putNotificationAccount(long accountId, String deviceToken) {

        Optional<NotificationAccountEntity> optional = notificationAccountRepository.
                findByAccountIdAndDeviceToken(accountId, deviceToken);

        if (optional.isPresent()) return;

        NotificationAccountEntity entity = new NotificationAccountEntity();

        entity.setAccountId(accountId);
        entity.setDeviceToken(deviceToken);

        notificationAccountRepository.save(entity);
    }

    public void deleteNotificationAccount(Long accountId, String deviceToken) {

        Optional<NotificationAccountEntity> entity = notificationAccountRepository.
                findByAccountIdAndDeviceToken(accountId, deviceToken);

        entity.ifPresent(notificationAccountEntity -> notificationAccountRepository.delete(notificationAccountEntity));
    }

    public void sendMessage(long pkId, String pkName, String title, String body, List<String> deviceTokens) throws FirebaseMessagingException {

        if (!deviceTokens.isEmpty()) {
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            String url = getLink(pkId, pkName);

            WebpushConfig webpushConfig = WebpushConfig.builder()
                    .setFcmOptions(
                            WebpushFcmOptions.builder()
                                    .setLink(url)
                                    .build()
                    ).build();

            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(notification)
                    .putData("pkId", String.valueOf(pkId))
                    .putData("pkName", pkName)
                    .putData("notificationUrl", url)
                    .setWebpushConfig(webpushConfig)
                    .addAllTokens(deviceTokens)
                    .build();

            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);

            response.getResponses().forEach(sendResponse -> {
                log.info("Đã gửi cho id: " + sendResponse.getMessageId());
            });
            log.info("Số thông báo gửi thành công: " + response.getSuccessCount());
            log.info("Số thông báo gửi lỗi: " + response.getFailureCount());
        }
    }

    public List<String> getTokens(long accountId) {

        return notificationAccountRepository
                .getAllByAccountId(accountId)
                .stream()
                .map(NotificationAccountEntity::getDeviceToken)
                .collect(Collectors.toList());
    }

    private String getLink(long pkId, String pkName) {

        switch (pkName) {
            case "contract":

                return "/personnel/" + pkName + "/detail/" + pkId;
            case "leave":
            case "absence":
            case "resignation":
                return "/application/" + pkName + "/detail/" + pkId;

        }

        return "";
    }

    @Autowired
    NotificationAccountRepository notificationAccountRepository;
}
