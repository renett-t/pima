package ru.renett.service.old.articleService;

import ru.renett.models.old.Article;
import ru.renett.models.old.Comment;
import ru.renett.models.old.Tag;
import ru.renett.models.User;
import ru.renett.repository.old.ArticleRepository;
import ru.renett.repository.old.CommentRepository;
import ru.renett.repository.old.TagRepository;
import ru.renett.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleGetDataServiceImpl implements ArticleGetDataService {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private TagRepository tagRepository;

    public ArticleGetDataServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, CommentRepository commentRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Article getArticleById(int id) {
        Optional<Article> foundArticle = articleRepository.findById(id);

        Article article = foundArticle.orElse(null);

        if (article != null) {
            User author = userRepository.findById(article.getAuthor().getId()).orElse(article.getAuthor());
            article.setAuthor(author);
            List<Comment> commentList = this.getArticleComments(article);
            article.setCommentAmount(commentList.size());
            article.setCommentList(this.rearrangeArticleCommentsList(commentList));
            article.setTagList(this.getArticleTags(article));
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
    public List<Tag> getArticleTags(Article article) {
        return tagRepository.findAllArticleTags(article.getId());
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
        List<Comment> commentList = commentRepository.findAllArticleComments(article.getId());
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
    public Tag getTagById(int tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }

    private void initializeArticlesWithBasicInfo(List<Article> articleList) {
        for (Article art : articleList) {
            this.initializeArticleWithBasicInfo(art);
        }
    }

    private void initializeArticleWithBasicInfo(Article article) {
        article.setCommentAmount(this.getArticleComments(article).size());
        article.setLikeAmount(this.getArticleLikesAmount(article));
        article.setTagList(this.getArticleTags(article));
    }
}
