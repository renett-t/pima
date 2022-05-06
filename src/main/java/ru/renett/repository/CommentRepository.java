package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.renett.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByArticleId(Long id);
}
