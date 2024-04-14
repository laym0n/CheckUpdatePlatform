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

import static org.junit.jupiter.api.Assertions.*;

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
        WebResourceDto webResourceDto = webResourceObservingDto.getWebResource();
        assertNotNull(webResourceDto);
        assertEquals(WebResourceDomainBuilder.DEFAULT_NAME, webResourceDto.getName());

        var observeSettings = webResourceObservingDto.getObserveSettings();
        if (entityObserveSettings == null) {
            assertNull(observeSettings);
        } else {
            assertEquals(entityObserveSettings.needNotify(), observeSettings.isNeedNotify());
        }
    }
}
