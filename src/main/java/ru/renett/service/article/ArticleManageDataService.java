package ru.renett.service.article;

import ru.renett.exceptions.FileUploadException;
import ru.renett.models.Article;
import ru.renett.models.Comment;
import ru.renett.models.User;

import javax.servlet.http.HttpServletRequest;

public interface ArticleManageDataService {
    Long createArticle(HttpServletRequest request) throws FileUploadException;
    void editArticle(HttpServletRequest request) throws FileUploadException;
    void deleteArticle(Article articleToDelete);
    void likeArticle(User user, Article likedArticle);
    void dislikeArticle(User user, Article dislikedArticle);
    void updateViewCount(Article article);
    void createComment(HttpServletRequest request);
    void deleteComment(Comment commentToDelete);
    void editComment(Comment editedComment);
}
