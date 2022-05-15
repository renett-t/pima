package ru.renett.utils.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.renett.dto.TagDto;
import ru.renett.models.Tag;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.service.article.TagsService;
import ru.renett.utils.TagsCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagsCacheImpl implements TagsCache {
    private final TagsService tagsService;

    private final Map<String, TagDto> mapOfTags = initializeMapOfTags();

    private Map<String, TagDto> initializeMapOfTags() {
        Map<String, TagDto> map = new HashMap<>();
        for (TagDto tag: tagsService.getAllTags()) {
            map.put(tag.getTitle(), tag);
        }

        return map;
    }

    public boolean containsTag(String tagParam) {
        return mapOfTags.containsKey(tagParam);
    }

    public TagDto getTagByName(String tagParam) {
        return mapOfTags.get(tagParam);
    }

    @Override
    public List<TagDto> getTags() {
        return new ArrayList<>(mapOfTags.values());
    }
}
