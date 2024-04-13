package com.victor.kochnev.dal.embeddable.object;

import com.victor.kochnev.dal.entity.converter.StringListConverter;
import com.victor.kochnev.dal.entity.converter.TagsInfoConverter;
import com.victor.kochnev.dal.entity.value.object.TagsInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import java.util.List;

/**
 * Подробное описание плагина
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddableSpecificPluginDescription {
    @Column(name = "description")
    private String description;
    @Column(name = "image_paths")
    @Convert(converter = StringListConverter.class)
    private List<String> imagePaths;
    @Column(name = "tags")
    @Convert(converter = TagsInfoConverter.class)
    @ColumnTransformer(write = "?::jsonb")
    @Builder.Default
    private TagsInfo tags = new TagsInfo();

    public TagsInfo getTags() {
        if (tags == null) {
            tags = new TagsInfo();
        }
        return tags;
    }
}
