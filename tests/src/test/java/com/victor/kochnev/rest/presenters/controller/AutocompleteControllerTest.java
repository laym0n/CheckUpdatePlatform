package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.response.AutocompleteTagsResponseDto;
import com.victor.kochnev.dal.entity.TagEntityBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AutocompleteControllerTest extends BaseControllerTest {
    private final String GET_TAGS_ENDPOINT = "/autocomplete/tags";

    @Test
    void getTags() {
        //Assign
        prepareDb();

        //Action
        MvcResult mvcResult = get(GET_TAGS_ENDPOINT, null, null);

        //Assert
        assertHttpStatusOk(mvcResult);

        var responseDto = getResponseDto(mvcResult, AutocompleteTagsResponseDto.class);
        assertNotNull(responseDto);
        assertNotNull(responseDto.getTags());
        assertEquals(4, responseDto.getTags().size());
    }

    private void prepareDb() {
        tagRepository.save(TagEntityBuilder.persistedDefaultBuilder()
                .data("tag1")
                .build());
        tagRepository.save(TagEntityBuilder.persistedDefaultBuilder()
                .data("tag2")
                .build());
        tagRepository.save(TagEntityBuilder.persistedDefaultBuilder()
                .data("tag3")
                .build());
        tagRepository.save(TagEntityBuilder.persistedDefaultBuilder()
                .data("tag4")
                .build());
    }
}
