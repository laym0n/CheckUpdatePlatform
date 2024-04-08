package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.BaseCoreUnitTest;
import com.victor.kochnev.core.dto.domain.entity.WebResourceDomainDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDtoBuilder;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.builder.WebResourceDomainBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DomainWebResourceMapperImplUnitTest extends BaseCoreUnitTest {

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
//        assertNull(webResource.getWebResourceObservingCollection());
    }

    @ParameterizedTest
    @ValueSource(strings = {"description"})
    @NullSource
    void testUpdate(String description) {
        //Assign
        WebResourcePluginDto dto = WebResourcePluginDtoBuilder.defaultBuilder().description(description).build();
        WebResource webResource = WebResourceDomainBuilder.persistedDefaultBuilder().build();
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
//        assertNull(webResource.getWebResourceObservingCollection());
    }

    @ParameterizedTest
    @ValueSource(strings = {"description"})
    @NullSource
    void testMapToDto(String description) {
        //Assign
        WebResource webResource = WebResourceDomainBuilder.persistedDefaultBuilder().description(description).build();

        //Action
        WebResourceDomainDto webResourceDomainDto = domainWebResourceMapper.mapToDto(webResource);

        //Assert
        assertEquals(webResource.getId(), webResourceDomainDto.getId());
        assertEquals(webResource.getName(), webResourceDomainDto.getName());
        assertEquals(webResource.getDescription(), webResourceDomainDto.getDescription());
    }
}
