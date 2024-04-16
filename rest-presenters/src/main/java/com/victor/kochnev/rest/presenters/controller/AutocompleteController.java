package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.response.AutocompleteTagsResponseDto;
import com.victor.kochnev.core.facade.autocomplete.AutocompleteFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autocomplete")
public class AutocompleteController {
    private static final String GET_TAGS_ENDPOINT = "GET /autocomplete/tags";
    private final AutocompleteFacade autocompleteFacade;

    @GetMapping("/autocomplete/tags")
    @Operation(operationId = "getTags")
    public ResponseEntity<AutocompleteTagsResponseDto> createPluginUsage() {
        log.info("Request: {}", GET_TAGS_ENDPOINT);

        var response = autocompleteFacade.getTags();

        log.info("Request: {} proccesed {}", GET_TAGS_ENDPOINT, response);
        return ResponseEntity.ok(response);
    }
}
