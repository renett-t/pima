package ru.renett.service.article;

import ru.renett.dto.rest.AddArticleDto;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.rest.ArticlesPage;
import ru.renett.dto.rest.UpdateArticleDto;

public interface ArticlesRestService {

    ArticlesPage getArticles(int page, int limit);

    ArticleDto addNewArticle(AddArticleDto addArticleDto);

    ArticleDto updateArticle(Long id, UpdateArticleDto updateArticleDto);

    void deleteArticleById(Long id);

    ArticleDto getArticleById(Long id);
}
