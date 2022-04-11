package ru.renett.service.old.articleService;

import ru.renett.exceptions.FileUploadException;
import ru.renett.models.old.Article;
import ru.renett.models.old.Comment;
import ru.renett.models.User;

import javax.servlet.http.HttpServletRequest;

public interface ArticleSaveDataService {
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
