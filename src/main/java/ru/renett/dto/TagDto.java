package ru.renett.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.renett.models.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private Long id;
    private String title;

    public static TagDto from(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .title(tag.getTitle())
                .build();
    }

    public static List<TagDto> from(List<Tag> tags) {
        return tags.stream().map(TagDto::from).collect(Collectors.toList());
    }

    public static Set<TagDto> from(Set<Tag> tags) {
        return tags.stream().map(TagDto::from).collect(Collectors.toSet());
    }
}
