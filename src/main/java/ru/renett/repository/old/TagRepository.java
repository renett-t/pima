package ru.renett.repository.old;

import ru.renett.models.old.Tag;

import java.util.List;

public interface TagRepository extends CRUDRepository<Tag> {
    List<Tag> findAllArticleTags(int articleId);
    void saveNewTags(List<Tag> newTags, int articleId);
    void deleteOldTags(List<Tag> oldTags, int articleId);
}
