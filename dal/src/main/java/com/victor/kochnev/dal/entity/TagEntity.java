package com.victor.kochnev.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "TAG")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TagEntity extends BaseDalEntity {
    @Column(name = "data")
    private String data;
}
