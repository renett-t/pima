package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.renett.dto.CommentDto;
import ru.renett.dto.form.CommentForm;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.models.Article;
import ru.renett.models.Comment;
import ru.renett.models.User;
import ru.renett.repository.ArticlesRepository;
import ru.renett.repository.CommentsRepository;
import ru.renett.repository.UsersRepository;
import ru.renett.service.article.CommentsService;
import ru.renett.utils.CommentsRearranger;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;
    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;
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

        Article article = articlesRepository.findById(form.getArticleId())
                .orElseThrow(() -> new EntityNotFoundException("Article with id = " + form.getArticleId() + " not found."));
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + userId + " not found."));

        Comment newComment = Comment.builder()
                .body(form.getBody())
                .article(article)  // maybe better to not create useless requests and just set new Article(id)
                .author(user)      // user by this moment is in persistent context btw
                .publishedAt(new Date())
                .parentComment(parentComment)
                .childComments(new ArrayList<>())
                .build();

        Comment comment = commentsRepository.save(newComment);
        return CommentDto.from(comment);
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
