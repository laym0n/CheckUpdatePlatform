package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.value.object.TagInfoDto;
import com.victor.kochnev.core.repository.TagRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true),
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class DomainTagMapper {
    @Autowired
    private TagRepository tagRepository;

    public List<TagInfoDto> mapToTagInfoDto(List<String> tags) {
        var allTagsResult = tagRepository.findAllByData(tags);
        return allTagsResult.stream()
                .map(findResult -> new TagInfoDto(findResult.data(), findResult.optionalTag().isEmpty()))
                .toList();
    }
}
