package ru.kpfu.itis.renett.repository;

import ru.kpfu.itis.renett.models.Comment;

import java.util.List;

public interface CommentRepository extends CRUDRepository<Comment> {
    List<Comment> findAllArticleComments(int articleId);
}
