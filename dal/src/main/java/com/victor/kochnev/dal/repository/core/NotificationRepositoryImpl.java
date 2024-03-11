package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.NotificationRepository;
import com.victor.kochnev.dal.converter.EntityNotificationMapper;
import com.victor.kochnev.dal.entity.NotificationEntity;
import com.victor.kochnev.dal.repository.jpa.NotificationEntityRepository;
import com.victor.kochnev.dal.repository.jpa.UserEntityRepository;
import com.victor.kochnev.domain.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationEntityRepository notificationRepository;
    private final UserEntityRepository userRepository;
    private final EntityNotificationMapper notificationMapper;

    @Override
    @Transactional
    public void createAll(List<Notification> notificationList) {
        List<NotificationEntity> notificationEntityList = notificationList.stream().map(notification -> {
            NotificationEntity notificationEntity = notificationMapper.mapToEntity(notification);
            notificationEntity.setUser(userRepository.findById(notification.getUser().getId()).get());
            return notificationEntity;
        }).toList();
        notificationRepository.saveAll(notificationEntityList);
    }
}
