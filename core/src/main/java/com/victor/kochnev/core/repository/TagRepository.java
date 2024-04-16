package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {
    List<Tag> getAll();

    List<TagFindResult> findAllByData(List<String> dataList);

    void createAll(List<Tag> tags);

    record TagFindResult(String data, Optional<Tag> optionalTag) {

    }
}