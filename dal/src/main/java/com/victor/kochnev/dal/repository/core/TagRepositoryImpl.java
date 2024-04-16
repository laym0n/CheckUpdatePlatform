package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.TagRepository;
import com.victor.kochnev.dal.converter.EntityTagMapper;
import com.victor.kochnev.dal.entity.TagEntity;
import com.victor.kochnev.dal.repository.jpa.TagEntityRepository;
import com.victor.kochnev.domain.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final TagEntityRepository tagEntityRepository;
    private final EntityTagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Tag> getAll() {
        return tagEntityRepository.findAll()
                .stream()
                .map(tagMapper::mapToDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagFindResult> findAllByData(List<String> dataList) {
        List<TagEntity> tagEntityList = tagEntityRepository.findAllByData(dataList);
        Map<String, TagEntity> tagEntityMap = tagEntityList.stream()
                .collect(Collectors.toMap(TagEntity::getData, tag -> tag));
        return dataList.stream()
                .map(data -> {
                    TagEntity tagEntity = tagEntityMap.get(data);
                    Optional<Tag> optionalTag = Optional.ofNullable(tagEntity).map(tagMapper::mapToDomain);
                    return new TagFindResult(data, optionalTag);
                }).toList();
    }

    @Override
    @Transactional
    public void createAll(List<Tag> tags) {
        List<TagEntity> tagEntityList = tags.stream().map(tagMapper::mapToEntity).toList();
        tagEntityRepository.saveAll(tagEntityList);
    }
}
