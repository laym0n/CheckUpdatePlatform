package com.victor.kochnev.core.service.feedback;

import com.victor.kochnev.core.converter.DomainFeedbackMapper;
import com.victor.kochnev.core.converter.RequestDtoMapper;
import com.victor.kochnev.core.dto.domain.entity.FeedbackDto;
import com.victor.kochnev.core.dto.request.CreateOrUpdateFeedbackRequestDto;
import com.victor.kochnev.core.dto.request.GetFeedbacksRequestDto;
import com.victor.kochnev.core.dto.response.GetFeedbacksResponseDto;
import com.victor.kochnev.core.repository.FeedbackRepository;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.domain.entity.Feedback;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final PluginRepository pluginRepository;
    private final UserRepository userRepository;
    private final DomainFeedbackMapper feedbackMapper;
    private final RequestDtoMapper requestDtoMapper;

    @Override
    @Transactional
    public FeedbackDto createOrUpdate(UUID userId, CreateOrUpdateFeedbackRequestDto requestDto) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findByUserIdAndPluginId(userId, requestDto.getPluginId());
        Feedback feedback;
        if (feedbackOptional.isPresent()) {
            feedback = feedbackOptional.get();
            feedbackMapper.update(feedback, requestDto);
            feedback = feedbackRepository.update(feedback);
        } else {
            feedback = feedbackMapper.mapToDomain(requestDto);
            feedback.setPlugin(pluginRepository.getById(requestDto.getPluginId()));
            feedback.setUser(userRepository.getById(userId));
            feedback = feedbackRepository.create(feedback);
        }
        return feedbackMapper.mapToDto(feedback);
    }

    @Override
    @Transactional
    public GetFeedbacksResponseDto getByFilters(GetFeedbacksRequestDto requestDto) {
        var dalRequestDto = requestDtoMapper.mapToDal(requestDto);
        var dalResponseDto = feedbackRepository.getByFilters(dalRequestDto);
        return feedbackMapper.mapToDto(dalResponseDto);
    }
}
