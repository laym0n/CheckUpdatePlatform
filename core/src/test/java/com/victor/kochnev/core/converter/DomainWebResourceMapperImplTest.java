package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.base.BaseCoreTest;
import com.victor.kochnev.core.dto.domain.entity.WebResourceDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDtoBuilder;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.builder.WebResourceBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DomainWebResourceMapperImplTest extends BaseCoreTest {

    @ParameterizedTest
    @ValueSource(strings = {"description"})
    @NullSource
    void testMapToEntity(String description) {
        //Assign
        WebResourcePluginDto dto = WebResourcePluginDtoBuilder.defaultBuilder()
                .description(description)
                .build();

        //Action
        WebResource webResource = domainWebResourceMapper.mapToEntity(dto);

        //Assert
        assertNotNull(webResource);
        assertNull(webResource.getId());
        assertNull(webResource.getCreateDate());
        assertNull(webResource.getLastChangeDate());
        assertNull(webResource.getVersion());

        assertEquals(description, webResource.getDescription());
        assertEquals(WebResourcePluginDtoBuilder.DEFAULT_NAME, webResource.getName());
        assertNull(webResource.getPlugin());
        assertNull(webResource.getWebResourceObservingCollection());
    }

    @ParameterizedTest
    @ValueSource(strings = {"description"})
    @NullSource
    void testUpdate(String description) {
        //Assign
        WebResourcePluginDto dto = WebResourcePluginDtoBuilder.defaultBuilder().description(description).build();
        WebResource webResource = WebResourceBuilder.persistedDefaultBuilder().build();
        ZonedDateTime createDate = webResource.getCreateDate();
        String expectedDescription = description != null ? description : webResource.getDescription();

        //Action
        domainWebResourceMapper.update(webResource, dto);

        //Assert
        assertNotNull(webResource);
        assertEquals(createDate, webResource.getCreateDate());

        assertEquals(expectedDescription, webResource.getDescription());
        assertEquals(WebResourcePluginDtoBuilder.DEFAULT_NAME, webResource.getName());
        assertNull(webResource.getPlugin());
        assertNull(webResource.getWebResourceObservingCollection());
    }

    @ParameterizedTest
    @ValueSource(strings = {"description"})
    @NullSource
    void testMapToUserDto(String description) {
        //Assign
        WebResource webResource = WebResourceBuilder.persistedDefaultBuilder().description(description).build();

        //Action
        WebResourceDto webResourceDto = domainWebResourceMapper.mapToDto(webResource);

        //Assert
        assertEquals(webResource.getId(), webResourceDto.getId());
        assertEquals(webResource.getName(), webResourceDto.getName());
        assertEquals(webResource.getDescription(), webResourceDto.getDescription());
    }
}
