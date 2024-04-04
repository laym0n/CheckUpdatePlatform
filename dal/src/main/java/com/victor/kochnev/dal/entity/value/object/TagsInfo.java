package com.victor.kochnev.dal.entity.value.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagsInfo {

    @Builder.Default
    private List<String> tags = new ArrayList<>();

    public List<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        return tags;
    }
}
