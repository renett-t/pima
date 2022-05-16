package ru.renett.service.article;

import ru.renett.dto.CommentDto;
import ru.renett.dto.form.CommentForm;

import java.util.List;

public interface CommentsService {
    List<CommentDto> getArticleComments(Long articleId);

    CommentDto createComment(CommentForm form, Long userId);

    void deleteComment(Long commentId);

    CommentDto editComment(Long commentId);
}
