package ru.renett.service.old.articleService;

import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.models.Article;
import ru.renett.models.Comment;
import ru.renett.models.Tag;
import ru.renett.models.User;

import java.util.List;
import java.util.Set;

public interface ArticlesGetDataService {
    Article getArticleById(Long id) throws ArticleNotFoundException;
    List<Article> getUsersArticles(User user);
    List<Article> getLikedArticles(User user);
    List<Article> getAllArticles();
    List<Article> getAllArticlesExceptUsers(User user);
    List<Article> getAllArticlesByTag(Tag tag);

    Tag getTagById(Long tagId);
    List<Tag> getAllTags();
    Set<Tag> getArticleTags(Article article);
    boolean isArticleLikedByUser(User user, Article article);
    long getArticleLikesAmount(Article article);

    List<Comment> getArticleComments(Article article);
    List<Comment> rearrangeArticleCommentsList(List<Comment> commentList);

    Article getArticleByIdOrSlug(String parameter) throws ArticleNotFoundException;
}
