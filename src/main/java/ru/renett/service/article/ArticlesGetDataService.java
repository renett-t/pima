package ru.renett.service.article;

import ru.renett.dto.ArticleDto;
import ru.renett.exceptions.ArticleNotFoundException;

import java.util.List;

public interface ArticlesGetDataService {
    ArticleDto getArticleById(Long id) throws ArticleNotFoundException;

    List<ArticleDto> getUsersArticles(Long userId);

    List<ArticleDto> getLikedArticles(Long userId);

    List<ArticleDto> getAllArticles();

    List<ArticleDto> getAllArticlesExceptUsers(Long userId);

    List<ArticleDto> getAllArticlesByTag(Long tagId);

    boolean isArticleLikedByUser(Long userId, Long articleId);

    int getArticleLikesAmount(Long articleId);

    ArticleDto getArticleByIdOrSlug(String parameter) throws ArticleNotFoundException;
}
