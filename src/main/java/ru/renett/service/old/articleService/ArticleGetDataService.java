package ru.renett.service.old.articleService;

import ru.renett.models.old.Article;
import ru.renett.models.old.Comment;
import ru.renett.models.old.Tag;
import ru.renett.models.User;

import java.util.List;

public interface ArticleGetDataService {
    Article getArticleById(Long id);
    List<Article> getUsersArticles(User user);
    List<Article> getLikedArticles(User user);
    List<Article> getAllArticles();
    List<Article> getAllArticlesExceptUsers(User user);
    List<Article> getAllArticlesByTag(Tag tag);

    Tag getTagById(Long tagId);
    List<Tag> getAllTags();
    List<Tag> getArticleTags(Article article);
    boolean isArticleLikedByUser(User user, Article article);
    long getArticleLikesAmount(Article article);

    List<Comment> getArticleComments(Article article);
    List<Comment> rearrangeArticleCommentsList(List<Comment> commentList);
}
