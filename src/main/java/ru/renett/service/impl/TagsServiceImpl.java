package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.renett.dto.TagDto;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.models.Article;
import ru.renett.models.Tag;
import ru.renett.repository.TagsRepository;
import ru.renett.service.article.TagsService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagsServiceImpl implements TagsService {
    private final TagsRepository tagsRepository;

    @Override
    public TagDto getTagById(Long tagId) {
        Tag tag = tagsRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag with id = " + tagId + " not found."));

        return TagDto.from(tag) ;
    }

    @Override
    public List<TagDto> getAllTags() {
        return TagDto.from(tagsRepository.findAll());
    }

    @Override
    public Set<TagDto> getArticleTags(Long articleId) {
        return TagDto.from(tagsRepository.findTagsByArticleId(articleId));
    }
}
