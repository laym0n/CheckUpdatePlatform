package com.victor.kochnev.core.facade.feedback;

import com.victor.kochnev.core.dto.domain.entity.FeedbackDto;
import com.victor.kochnev.core.dto.request.CreateOrUpdateFeedbackRequestDto;
import com.victor.kochnev.core.dto.request.GetFeedbacksRequestDto;
import com.victor.kochnev.core.dto.response.GetFeedbacksResponseDto;

public interface FeedbackFacade {
    FeedbackDto createOrUpdate(CreateOrUpdateFeedbackRequestDto requestDto);

    GetFeedbacksResponseDto get(GetFeedbacksRequestDto requestDto);
}
