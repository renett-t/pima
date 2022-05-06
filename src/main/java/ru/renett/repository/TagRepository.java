package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.renett.models.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findTagsByArticleId(int articleId); // todo :(
//    void saveNewTags(List<Tag> newTags, int articleId);
//    void deleteOldTags(List<Tag> oldTags, int articleId);
}
