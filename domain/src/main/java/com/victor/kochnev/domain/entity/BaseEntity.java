package com.victor.kochnev.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
    private UUID id;
    private ZonedDateTime createDate;
    private ZonedDateTime lastChangeDate;
    private Long version;
}
