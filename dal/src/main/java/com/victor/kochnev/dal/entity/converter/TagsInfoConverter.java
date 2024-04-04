package com.victor.kochnev.dal.entity.converter;

import com.victor.kochnev.dal.entity.value.object.TagsInfo;

public class TagsInfoConverter extends JsonConverter<TagsInfo> {

    @Override
    protected Class<TagsInfo> getMappingClass() {
        return TagsInfo.class;
    }
}
