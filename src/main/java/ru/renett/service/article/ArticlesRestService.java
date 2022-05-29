package ru.renett.service.article;

import ru.renett.dto.rest.AddArticleDto;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.rest.ArticlesPage;
import ru.renett.dto.rest.CommentsPage;
import ru.renett.dto.rest.UpdateArticleDto;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.exceptions.InvalidArticlesRequestException;

public interface ArticlesRestService {

    ArticlesPage getArticles(int page, int limit) throws InvalidArticlesRequestException, EntityNotFoundException;
    CommentsPage getCommentsByArticleId(Long id) throws EntityNotFoundException;

    ArticleDto addNewArticle(AddArticleDto addArticleDto);

    ArticleDto updateArticle(Long id, UpdateArticleDto updateArticleDto);

    void deleteArticleById(Long id);

    ArticleDto getArticleById(Long id);
}
