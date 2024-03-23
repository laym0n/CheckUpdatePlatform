package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescription;
import com.victor.kochnev.domain.enums.TaskDecision;
import com.victor.kochnev.domain.enums.TaskType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "TASK")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TaskEntity extends BaseDalEntity {
    @Embedded
    private EmbeddablePluginDescription description;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TaskType type;
    @Column(name = "decision")
    @Enumerated(EnumType.STRING)
    private TaskDecision decision;
    @Column(name = "comment")
    private String comment;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plugin_id")
    private PluginEntity plugin;
}
