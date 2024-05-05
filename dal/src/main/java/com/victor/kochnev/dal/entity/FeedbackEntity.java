package com.victor.kochnev.dal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "FEEDBACK")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class FeedbackEntity extends BaseDalEntity {
    /**
     * Комментарий
     */
    @Column(name = "comment")
    private String comment;
    /**
     * Оценка
     */
    @Column(name = "rating")
    private Integer rating;
    /**
     * Пользователь, оставивиший оценку
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    /**
     * Плагин, на котоорый оставили оценку
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plugin_id")
    private PluginEntity plugin;
}
