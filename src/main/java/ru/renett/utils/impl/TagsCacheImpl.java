package ru.renett.utils.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.renett.models.Tag;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.utils.TagsCache;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TagsCacheImpl implements TagsCache {
    private final ArticlesGetDataService articlesGetDataService;

    private final Map<String, Tag> mapOfTags = initializeMapOfTags();

    private Map<String, Tag> initializeMapOfTags() {
        Map<String, Tag> map = new HashMap<>();
        for (Tag tag: articlesGetDataService.getAllTags()) {
            map.put(tag.getTitle(), tag);
        }

        return map;
    }

    public boolean containsTag(String tagParam) {
        return mapOfTags.containsKey(tagParam);
    }

    public Tag getTagByName(String tagParam) {
        return mapOfTags.get(tagParam);
    }
}
