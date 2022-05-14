package ru.renett.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleDto {
    private Long authorId;
    private String title;
    private String body;
    private String image;
    private List<String> tags;
}
