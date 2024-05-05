package com.victor.kochnev.core.facade.feedback;

import com.victor.kochnev.core.dto.domain.entity.FeedbackDto;
import com.victor.kochnev.core.dto.request.CreateOrUpdateFeedbackRequestDto;
import com.victor.kochnev.core.dto.request.GetFeedbacksRequestDto;
import com.victor.kochnev.core.dto.response.GetFeedbacksResponseDto;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.core.service.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class FeedbackFacadeImpl implements FeedbackFacade {
    private final FeedbackService feedbackService;
    private final SecurityUserService securityUserService;

    @Override
    @PreAuthorize("@authorizationService.verifyAuthenticatedUserCanCreateOrUpdateFeedback(#requestDto.getPluginId())")
    public FeedbackDto createOrUpdate(@P("requestDto") CreateOrUpdateFeedbackRequestDto requestDto) {
        UUID userId = securityUserService.getCurrentUser().getId();
        return feedbackService.createOrUpdate(userId, requestDto);
    }

    @Override
    public GetFeedbacksResponseDto get(GetFeedbacksRequestDto requestDto) {
        return feedbackService.getByFilters(requestDto);
    }
}
