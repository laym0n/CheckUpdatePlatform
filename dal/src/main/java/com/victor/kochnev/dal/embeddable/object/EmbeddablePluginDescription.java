package com.victor.kochnev.dal.embeddable.object;

import com.victor.kochnev.dal.entity.converter.DistributionMethodListConverter;
import com.victor.kochnev.dal.entity.converter.StringListConverter;
import com.victor.kochnev.domain.value.object.DistributionMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Подробное описание плагина
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddablePluginDescription {
    @Column(name = "description")
    private String description;
    @Column(name = "distribution_methods")
    @Convert(converter = DistributionMethodListConverter.class)
    private List<DistributionMethod> distributionMethods;
    @Column(name = "image_paths")
    @Convert(converter = StringListConverter.class)
    private List<String> imagePaths;
}
