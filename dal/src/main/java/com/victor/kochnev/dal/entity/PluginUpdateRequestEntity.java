package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescription;
import com.victor.kochnev.domain.enums.UpdateType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "PLUGIN_UPDATE_REQUEST")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class PluginUpdateRequestEntity extends BaseDalEntity {
    @Embedded
    private EmbeddablePluginDescription description;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UpdateType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plugin_id")
    private PluginEntity plugin;
}
