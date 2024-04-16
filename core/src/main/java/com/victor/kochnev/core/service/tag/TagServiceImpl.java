package com.victor.kochnev.core.service.tag;

import com.victor.kochnev.core.dto.response.AutocompleteTagsResponseDto;
import com.victor.kochnev.core.repository.TagRepository;
import com.victor.kochnev.domain.entity.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public AutocompleteTagsResponseDto getAllTags() {
        List<Tag> allTags = tagRepository.getAll();

        List<String> tagList = allTags.stream().map(Tag::getData).toList();
        return new AutocompleteTagsResponseDto(tagList);
    }

    @Override
    public void createAllIfNotExists(List<String> tags) {
        List<TagRepository.TagFindResult> findResultList = tagRepository.findAllByData(tags);
        List<Tag> notExistsTags = findResultList.stream()
                .filter(result -> result.optionalTag().isEmpty())
                .map(result -> new Tag(result.data()))
                .toList();
        tagRepository.createAll(notExistsTags);
    }
}
