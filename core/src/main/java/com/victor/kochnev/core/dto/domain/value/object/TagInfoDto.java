package com.victor.kochnev.core.dto.domain.value.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagInfoDto {
    @JsonProperty("tag")
    @NotNull
    private String tag;
    @JsonProperty("isNew")
    @NotNull
    private boolean isNew;
}
