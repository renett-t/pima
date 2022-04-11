package ru.renett.repository.old;

import ru.renett.models.old.Comment;

import java.util.List;

public interface CommentRepository extends CRUDRepository<Comment> {
    List<Comment> findAllArticleComments(int articleId);
}
