package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.renett.dto.CommentDto;
import ru.renett.dto.form.CommentForm;
import ru.renett.models.Article;
import ru.renett.models.Comment;
import ru.renett.models.User;
import ru.renett.repository.CommentsRepository;
import ru.renett.service.article.CommentsService;
import ru.renett.utils.CommentsRearranger;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;
    private final CommentsRearranger commentsRearranger;

    @Override
    public List<CommentDto> getArticleComments(Long articleId) {
        return CommentDto.from(
                commentsRearranger.rearrangeCommentsList(
                        commentsRepository.findCommentsByArticleId(articleId)
                ));
    }

    @Override
    @Transactional
    public CommentDto createComment(CommentForm form, Long userId) {
        Comment parentComment = null;
        if (form.getParentId() != -1) {
            parentComment = commentsRepository.getById(form.getParentId());
        }

        Comment newComment = Comment.builder()
                .body(form.getBody())
                .article(new Article(form.getArticleId())) // todo: is it ok?
                .author(new User(userId))
                .parentComment(parentComment)
                .build();

        commentsRepository.save(newComment);
        return CommentDto.from(newComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        throw new UnsupportedOperationException(":)");
    }

    @Override
    public CommentDto editComment(Long commentId) {
        throw new UnsupportedOperationException(":)");
    }
}
