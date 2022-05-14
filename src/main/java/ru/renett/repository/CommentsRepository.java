package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.renett.models.Comment;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByArticleId(Long id);

    //language=sql
   String SQL_FIND_ALL_COMMENTS_BY_ARTICLE_ID = "WITH RECURSIVE _comment AS\n" +
            "                   (SELECT id, body, author_id, article_id, parent_comment_id, published_at,\n" +
            "                           1 AS level\n" +
            "                    FROM comment\n" +
            "                    WHERE parent_comment_id IS NULL AND article_id = ?\n" +
            "                    UNION\n" +
            "                    SELECT comment.id, comment.body, comment.author_id, comment.article_id, comment.parent_comment_id, comment.published_at,\n" +
            "                           (level + 1) AS level\n" +
            "                    FROM comment INNER JOIN _comment ON _comment.id = comment.parent_comment_id)\n" +
            "SELECT * FROM _comment ORDER BY level;";
}
