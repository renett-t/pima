package ru.renett.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesPage {
    private List<ArticleDto> articles;
    private int page;
    private int limit;
    private int totalPages;
    private long totalItems;
}
