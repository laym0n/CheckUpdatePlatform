package com.victor.kochnev.core.facade.notification;

import com.victor.kochnev.core.dto.request.SendNotificationRequestDto;
import com.victor.kochnev.core.exception.CoreException;
import com.victor.kochnev.core.service.notification.NotificationService;
import com.victor.kochnev.core.service.user.UserService;
import com.victor.kochnev.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationFacadeImpl implements NotificationFacade {
    private final NotificationService notificationService;
    private final UserService userService;
    private final ExecutorService executorService;

    @Override
    public void sendNotification(SendNotificationRequestDto request) {
        List<User> observersList = userService.findAllObserversOfWebResource(request.getPluginId(), request.getWebResourceName());

        var sendNotificationsToUserStream = observersList.stream()
                .map(user -> CompletableFuture.runAsync(() -> notificationService.sendNotification(request, user), executorService));

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(sendNotificationsToUserStream.toArray(CompletableFuture[]::new));
        try {
            voidCompletableFuture.get(30, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            log.error(ExceptionUtils.getMessage(ex));
            Thread.currentThread().interrupt();
            throw new CoreException("", ex);
        } catch (ExecutionException | TimeoutException ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new CoreException("", ex);
        }
    }
}
