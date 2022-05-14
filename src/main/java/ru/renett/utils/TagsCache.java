package ru.renett.utils;

import ru.renett.models.Tag;

public interface TagsCache {
    boolean containsTag(String tagParam);

    Tag getTagByName(String tagParam);

}
