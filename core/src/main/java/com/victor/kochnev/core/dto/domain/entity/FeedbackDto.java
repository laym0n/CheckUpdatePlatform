package com.victor.kochnev.core.dto.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    @JsonProperty("id")
    @NotNull
    private UUID id;
    @JsonProperty("createDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm Z")
    @NotNull
    private ZonedDateTime createDate;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("rating")
    @NotNull
    private Integer rating;
    @JsonProperty("user")
    @NotNull
    private UserInfoDto user;
}
