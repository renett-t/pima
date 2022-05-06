package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.renett.models.Tag;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT * FROM tag left join article_tag on tag.id = article_tag.tag_id WHERE article_id = ?;", nativeQuery = true)
    Set<Tag> findTagsByArticleId(Long articleId);

    // INSERT INTO article_tag(article_id, tag_id) VALUES (?, ?);
//    void saveNewTags(Set<Tag> newTags, Long articleId);

//    @Query(value = "DELETE FROM article_tag WHERE article_id = ? AND tag_id=?;", nativeQuery = true)
//    void deleteOldTags(Set<Tag> oldTags, Long articleId);
}
