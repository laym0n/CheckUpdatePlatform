package com.victor.kochnev.core.facade.autocomplete;

import com.victor.kochnev.core.dto.response.AutocompleteTagsResponseDto;
import com.victor.kochnev.core.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AutocompleteFacadeImpl implements AutocompleteFacade {
    private final TagService tagService;

    @Override
    public AutocompleteTagsResponseDto getTags() {
        return tagService.getAllTags();
    }
}
