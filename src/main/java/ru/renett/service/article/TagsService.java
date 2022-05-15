package ru.renett.service.article;

import ru.renett.dto.TagDto;

import java.util.List;
import java.util.Set;

public interface TagsService {
    TagDto getTagById(Long tagId);
    List<TagDto> getAllTags();
    Set<TagDto> getArticleTags(Long articleId);
}
