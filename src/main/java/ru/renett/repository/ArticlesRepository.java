package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.renett.models.Article;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticlesRepository extends JpaRepository<Article, Long> {
    @Query(value = "SELECT * FROM article WHERE author_id = ? ORDER BY id;", nativeQuery = true)
    List<Article> findAllByAuthorId(Long ownerId);

    @Query(value = "SELECT * FROM article_tag LEFT JOIN article a on a.id = article_tag.article_id WHERE article_tag.tag_id = ? ORDER by a.id;", nativeQuery = true)
    List<Article> findAllByTagId(Long tagId);

    @Query(value = "SELECT * FROM like_article LEFT JOIN article a on a.id = like_article.article_id WHERE like_article.user_id = ? ORDER BY a.id;", nativeQuery = true)
    List<Article> findAllLikedArticles(Long userId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM like_article WHERE user_id = ? AND article_id = ?;", nativeQuery = true)
    void removeLikeFromArticle(Long userId, Long articleId);

    @Query(value = "SELECT COUNT(user_id) FROM like_article WHERE article_id = ?;", nativeQuery = true)
    long getLikesAmount(Long articleId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE article set view_count = ? WHERE id = ?;", nativeQuery = true)
    void updateViewCount(Long viewCount, Long articleId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE article set title = ?, body = ? WHERE id=?;", nativeQuery = true)
    void updateWithoutThumbnail(Article article);
}
