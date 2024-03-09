package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescription;
import com.victor.kochnev.dal.entity.converter.DistributionMethodListConverter;
import com.victor.kochnev.dal.entity.converter.StringListConverter;
import com.victor.kochnev.domain.value.object.DistributionMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "PLUGIN")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class PluginEntity extends BaseDalEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "base_url")
    private String baseUrl;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "image_paths")
    @Convert(converter = StringListConverter.class)
    private List<String> imagePathsList;
    @Embedded
    private EmbeddablePluginDescription description;
    @Column(name = "distribution_methods")
    @Convert(converter = DistributionMethodListConverter.class)
    private List<DistributionMethod> distributionMethodsCollection;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "owner_user_id")
    private UserEntity ownerUser;
    @OneToMany(mappedBy = "plugin")
    private List<WebResourceEntity> webResourcesList;
    @OneToMany(mappedBy = "plugin")
    private List<PluginUsageEntity> pluginUsageEntityList;
}
