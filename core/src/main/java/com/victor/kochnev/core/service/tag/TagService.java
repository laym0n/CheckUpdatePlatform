package com.victor.kochnev.core.service.tag;

import com.victor.kochnev.core.dto.response.AutocompleteTagsResponseDto;

import java.util.List;

public interface TagService {
    AutocompleteTagsResponseDto getAllTags();

    void createAllIfNotExists(List<String> tags);
}
