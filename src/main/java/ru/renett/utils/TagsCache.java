package ru.renett.utils;

import ru.renett.dto.TagDto;

import java.util.List;

public interface TagsCache {
    boolean containsTag(String tagParam);

    TagDto getTagByName(String tagParam);

    List<TagDto> getTags();
}
