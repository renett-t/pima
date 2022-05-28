package ru.renett.utils.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.renett.dto.TagDto;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.service.article.TagsService;
import ru.renett.utils.TagsCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TagsCacheImpl implements TagsCache {
    private final TagsService tagsService;

    private Map<String, TagDto> mapOfTags = new HashMap<>();
    private boolean isInitialized = false;

    private Map<String, TagDto> initializeMapOfTags() {
        Map<String, TagDto> map = new HashMap<>();
        for (TagDto tag : tagsService.getAllTags()) {
            map.put(tag.getTitle(), tag);
        }

        return map;
    }

    public boolean containsTag(String tagParam) {
        checkInitialization();
        return mapOfTags.containsKey(tagParam);
    }

    private void checkInitialization() {
        if (!isInitialized) {
            this.mapOfTags = initializeMapOfTags();
            isInitialized = true;
        }
    }

    public TagDto getTagByName(String tagParam) {
        checkInitialization();
        return mapOfTags.get(tagParam);
    }

    @Override
    public List<TagDto> getTags() {
        checkInitialization();
        return new ArrayList<>(mapOfTags.values());
    }

    @Override
    public TagDto getTagById(String id) {
        try {
            Long tagId = Long.parseLong(id);
            for (TagDto tag : mapOfTags.values()) {
                if (tag.getId().equals(tagId)) {
                    return tag;
                }
            }
            throw new EntityNotFoundException("Tag with id = " + tagId + " not found.");
        } catch (NumberFormatException ex) {
            throw new EntityNotFoundException("Id is invalid. Given param=" + id);
        }
    }
}
