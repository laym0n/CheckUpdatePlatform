package com.victor.kochnev.dal.embeddable.object;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddableObserveSettings {
    @Column(name = "need_notify")
    private Boolean needNotify;
}
