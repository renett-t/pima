package ru.renett;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.renett.dto.TagDto;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.utils.TagsCache;

@Component
@RequiredArgsConstructor
public class StringToTagConverter implements Converter<String, TagDto> {
    private final TagsCache tagsCache;

    @Override
    public TagDto convert(String source) {
        try {
            return tagsCache.getTagById(source);
        } catch (EntityNotFoundException e) {
            return TagDto.builder()
                    .id(-1L)
                    .title("-1")
                    .build();
        }
    }
}
