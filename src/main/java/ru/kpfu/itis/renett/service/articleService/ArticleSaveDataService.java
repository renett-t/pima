package ru.kpfu.itis.renett.service.articleService;

import ru.kpfu.itis.renett.exceptions.FileUploadException;
import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Comment;
import ru.kpfu.itis.renett.models.User;

import javax.servlet.http.HttpServletRequest;

public interface ArticleSaveDataService {
    int createArticle(HttpServletRequest request) throws FileUploadException;
    void editArticle(HttpServletRequest request) throws FileUploadException;
    void deleteArticle(Article articleToDelete);
    void likeArticle(User user, Article likedArticle);
    void dislikeArticle(User user, Article dislikedArticle);
    void updateViewCount(Article article);
    void createComment(HttpServletRequest request);
    void deleteComment(Comment commentToDelete);
    void editComment(Comment editedComment);
}
