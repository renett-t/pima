package ru.renett.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.renett.dto.ArticleDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesPage {
    private String message;
    private List<ArticleDto> articles;
    private int page;
    private int limit;
    private int totalPages;
    private long totalItems;
}
