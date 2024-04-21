package com.victor.kochnev.core.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetWebResouceObservingsResponseDto {
    @JsonProperty("webResourceObservings")
    @NotNull
    private List<WebResourceObservingDto> webResourceObservings;
}
