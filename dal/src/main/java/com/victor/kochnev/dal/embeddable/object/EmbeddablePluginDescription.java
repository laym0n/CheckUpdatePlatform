package com.victor.kochnev.dal.embeddable.object;

import com.victor.kochnev.dal.entity.converter.DistributionMethodListConverter;
import com.victor.kochnev.domain.value.object.DistributionMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Описание плагина
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddablePluginDescription {
    @Embedded
    private EmbeddableSpecificPluginDescription specificDescription;
    @Column(name = "logo_path")
    private String logoPath;
    @Column(name = "distribution_methods")
    @Convert(converter = DistributionMethodListConverter.class)
    private List<DistributionMethod> distributionMethods;
}
