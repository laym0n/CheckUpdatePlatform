package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.BaseCoreUnitTest;
import com.victor.kochnev.core.dto.domain.entity.WebResourceDto;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.entity.builder.WebResourceDomainBuilder;
import com.victor.kochnev.domain.entity.builder.WebResourceObservingBuilder;
import com.victor.kochnev.domain.value.object.ObserveSettings;
import com.victor.kochnev.domain.value.object.ObserveSettingsBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DomainWebResourceObservingMapperImplUnitTest extends BaseCoreUnitTest {

    private static Stream<Arguments> testMapToDtoArguments() {
        return Stream.of(Arguments.of(ObserveSettingsBuilder.defaultObserveSettings()));
    }

    @ParameterizedTest
    @MethodSource("testMapToDtoArguments")
    @NullSource
    void testMapToDto(ObserveSettings entityObserveSettings) {
        //Assign
        WebResourceObserving webResourceObserving = WebResourceObservingBuilder.persistedDefaultBuilder()
                .observeSettings(entityObserveSettings)
                .build();

        //Action
        WebResourceObservingDto webResourceObservingDto = domainWebResourceObservingMapper.mapToDto(webResourceObserving);

        //Assert
        WebResourceDto webResourceDto = webResourceObservingDto.getWebResourceDto();
        assertNotNull(webResourceDto);
        assertEquals(WebResourceDomainBuilder.DEFAULT_NAME, webResourceDto.getName());

        ObserveSettings observeSettings = webResourceObservingDto.getObserveSettings();
        assertEquals(entityObserveSettings, observeSettings);
    }
}
