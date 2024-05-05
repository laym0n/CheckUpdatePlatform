package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.FeedbackRepository;
import com.victor.kochnev.dal.converter.EntityFeedbackMapper;
import com.victor.kochnev.dal.entity.FeedbackEntity;
import com.victor.kochnev.dal.repository.jpa.FeedbackEntityRepository;
import com.victor.kochnev.dal.repository.jpa.PluginEntityRepository;
import com.victor.kochnev.dal.repository.jpa.UserEntityRepository;
import com.victor.kochnev.domain.entity.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FeedbackRepositoryImpl implements FeedbackRepository {
    private final FeedbackEntityRepository feedbackRepository;
    private final PluginEntityRepository pluginEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final EntityFeedbackMapper feedbackMapper;

    @Override
    @Transactional
    public Feedback create(Feedback feedback) {
        FeedbackEntity feedbackEntity = feedbackMapper.mapToDal(feedback);
        feedbackEntity.setPlugin(pluginEntityRepository.findById(feedback.getPlugin().getId()).get());
        feedbackEntity.setUser(userEntityRepository.findById(feedback.getUser().getId()).get());
        feedbackEntity = feedbackRepository.save(feedbackEntity);
        return feedbackMapper.mapToDomain(feedbackEntity);
    }

    @Override
    @Transactional
    public Feedback update(Feedback feedback) {
        FeedbackEntity feedbackEntity = feedbackRepository.findById(feedback.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Feedback", "id", feedback.getId().toString()));
        feedbackMapper.update(feedbackEntity, feedback);
        feedbackEntity = feedbackRepository.save(feedbackEntity);
        return feedbackMapper.mapToDomain(feedbackEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Feedback> findByUserIdAndPluginId(UUID userId, UUID pluginId) {
        return feedbackRepository.findByUserIdAndPluginId(userId, pluginId)
                .map(feedbackMapper::mapToDomain);
    }
}
