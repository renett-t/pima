package ru.renett.service.old.articleService;

import lombok.RequiredArgsConstructor;
import ru.renett.models.Article;
import ru.renett.models.Comment;
import ru.renett.models.Tag;
import ru.renett.models.User;
import ru.renett.repository.ArticleRepository;
import ru.renett.repository.CommentRepository;
import ru.renett.repository.TagRepository;
import ru.renett.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class ArticleGetDataServiceImpl implements ArticleGetDataService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;

    @Override
    public Article getArticleById(Long id) {
        Optional<Article> foundArticle = articleRepository.findById(id);

        Article article = foundArticle.orElse(null);

        if (article != null) {
            User author = userRepository.findById(article.getAuthor().getId()).orElse(article.getAuthor());
            article.setAuthor(author);
            List<Comment> commentList = this.getArticleComments(article);
            article.setCommentAmount((long) commentList.size());
            article.setCommentList(this.rearrangeArticleCommentsList(commentList));
            article.setTags(this.getArticleTags(article));
            article.setLikeAmount(this.getArticleLikesAmount(article));
        }

        return article;
    }

    @Override
    public List<Article> getUsersArticles(User user) {
        if (user != null) {
            List<Article> articleList = articleRepository.findAllByAuthorId(user.getId());
            initializeArticlesWithBasicInfo(articleList);
            return articleList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Article> getLikedArticles(User user) {
        if (user != null) {
            List<Article> articleList = articleRepository.findAllLikedArticles(user.getId());
            initializeArticlesWithBasicInfo(articleList);
            return articleList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articleList = articleRepository.findAll();
        initializeArticlesWithBasicInfo(articleList);
        return articleList;
    }

    @Override
    public List<Article> getAllArticlesExceptUsers(User user) {
        List<Article> all = articleRepository.findAll();
        if (user != null) {
            List<Article> users = articleRepository.findAllByAuthorId(user.getId());
            all.removeAll(users);
            initializeArticlesWithBasicInfo(all);
        }
        return all;
    }

    @Override
    public List<Article> getAllArticlesByTag(Tag tag) {
        if (tag != null) {
            List<Article> articleList = articleRepository.findAllByTagId(tag.getId());
            initializeArticlesWithBasicInfo(articleList);
            return articleList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Set<Tag> getArticleTags(Article article) {
        return tagRepository.findTagsByArticleId(article.getId());
    }


    @Override
    public boolean isArticleLikedByUser(User user, Article article) {
        if (user != null && article != null) {
            List<Article> articleList = this.getLikedArticles(user);
            return articleList.contains(article);
        }
        return false;
    }

    @Override
    public long getArticleLikesAmount(Article article) {
        return articleRepository.getLikesAmount(article.getId());
    }

    /**
     * Not rearranged comment list. Call rearrangeArticleCommentsList before displaying article
     * @param article
     * @return raw list of article's comments
     */
    @Override
    public List<Comment> getArticleComments(Article article) {
        List<Comment> commentList = commentRepository.findCommentsByArticleId(article.getId());
        for (Comment comment : commentList) {
            User actualAuthor = userRepository.findById(comment.getAuthor().getId()).orElse(comment.getAuthor());
            comment.setAuthor(actualAuthor);
        }
        return commentList;
    }

    @Override
    public List<Comment> rearrangeArticleCommentsList(List<Comment> commentList) {
        for (int i = commentList.size() - 1; i >= 0; i--) {                                                                 // нужно идти с конца, так как id вложенных комментариев точно больше родительских
            Comment child = commentList.get(i);
            if (child.getParentComment() != null) {
                commentList.remove(i);
                for (int k = 0; k < commentList.size(); k++) {
                    if (commentList.get(k).getId().equals(child.getParentComment().getId())){
                        commentList.get(k).getChildComments().add(child);
                        break;
                    }
                }
            }
        }

        for (int j = 0; j < commentList.size(); j++) {
            List<Comment> listToReverse = commentList.get(j).getChildComments();
            for (int i = 0; i < listToReverse.size() / 2; i++) {
                Comment temp = listToReverse.get(i);
                listToReverse.set(i, listToReverse.get(listToReverse.size() - i - 1));
                listToReverse.set(listToReverse.size() - i - 1, temp);
            }

        }
        return commentList;
    }

    @Override
    public Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }

    private void initializeArticlesWithBasicInfo(List<Article> articleList) {
        for (Article art : articleList) {
            this.initializeArticleWithBasicInfo(art);
        }
    }

    private void initializeArticleWithBasicInfo(Article article) {
        article.setCommentAmount((long) this.getArticleComments(article).size());
        article.setLikeAmount(this.getArticleLikesAmount(article));
        article.setTags(this.getArticleTags(article));
    }
}
