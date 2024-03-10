package com.victor.kochnev.dal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "NOTIFICATION")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class NotificationEntity extends BaseDalEntity {
    @Column(name = "message")
    private String message;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
