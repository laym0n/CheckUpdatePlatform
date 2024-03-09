package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.embeddable.object.EmbeddableObserveSettings;
import com.victor.kochnev.domain.enums.ObserveStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Table(name = "WEBRESOURCE_OBSERVING")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class WebResourceObservingEntity extends BaseDalEntity {
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ObserveStatus status;
    @Embedded
    private EmbeddableObserveSettings observeSettings;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.EAGER/*, cascade = {CascadeType.MERGE, CascadeType.PERSIST}*/)
    @JoinColumn(name = "webresource_id")
    private WebResourceEntity webResource;
}
