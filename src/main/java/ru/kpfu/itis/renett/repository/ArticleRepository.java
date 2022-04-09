package ru.kpfu.itis.renett.repository;

import ru.kpfu.itis.renett.models.Article;

import java.util.List;

public interface ArticleRepository extends CRUDRepository<Article> {
    List<Article> findAllByOwnerId(int ownerId);
    List<Article> findAllByTagId(int tagId);
    List<Article> findAllLikedArticles(int userId);
    void updateLikesAmount(int userId, int articleId);
    void removeLikeFromArticle(int userId, int articleId);
    int getLikesAmount(int articleId);
    void updateViewCount(int articleId, Long viewCount);
    void updateWithoutThumbnail(Article article);
}
