package com.victor.kochnev.dal.entity;

import com.victor.kochnev.domain.enums.ObserveStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "WEBRESOURCE")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class WebResourceEntity extends BaseDalEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ObserveStatus status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plugin_id")
    private PluginEntity plugin;
//    private Collection<WebResourceObservingEntity> webResourceObservingCollection;
}
