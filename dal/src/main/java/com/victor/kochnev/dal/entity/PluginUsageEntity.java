package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.embeddable.object.EmbeddableDistributionMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(name = "PLUGIN_USAGE")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class PluginUsageEntity extends BaseDalEntity {
    @Embedded
    private EmbeddableDistributionMethod distributionMethod;
    @Column(name = "expired_date")
    private ZonedDateTime expiredDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plugin_id")
    private PluginEntity plugin;
}
