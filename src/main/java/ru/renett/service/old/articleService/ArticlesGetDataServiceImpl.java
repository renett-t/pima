package ru.renett.service.old.articleService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.renett.models.Article;
import ru.renett.models.Comment;
import ru.renett.models.Tag;
import ru.renett.models.User;
import ru.renett.repository.ArticlesRepository;
import ru.renett.repository.CommentsRepository;
import ru.renett.repository.TagsRepository;
import ru.renett.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticlesGetDataServiceImpl implements ArticlesGetDataService {
    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;
    private final CommentsRepository commentsRepository;
    private final TagsRepository tagsRepository;

    @Override
    public Article getArticleById(Long id) {
        Optional<Article> foundArticle = articlesRepository.findById(id);

        Article article = foundArticle.orElse(null);

        if (article != null) {
            User author = usersRepository.findById(article.getAuthor().getId()).orElse(article.getAuthor());
            article.setAuthor(author);
            List<Comment> commentList = this.getArticleComments(article);
            article.setCommentAmount(commentList.size());
            article.setCommentList(this.rearrangeArticleCommentsList(commentList));
            article.setTags(this.getArticleTags(article));
            article.setLikeAmount((int) this.getArticleLikesAmount(article));
        }

        return article;
    }

    @Override
    public List<Article> getUsersArticles(User user) {
        if (user != null) {
            List<Article> articleList = articlesRepository.findAllByAuthorId(user.getId());
            initializeArticlesWithBasicInfo(articleList);
            return articleList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Article> getLikedArticles(User user) {
        if (user != null) {
            List<Article> articleList = articlesRepository.findAllLikedArticles(user.getId());
            initializeArticlesWithBasicInfo(articleList);
            return articleList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articleList = articlesRepository.findAll();
        initializeArticlesWithBasicInfo(articleList);
        return articleList;
    }

    @Override
    public List<Article> getAllArticlesExceptUsers(User user) {
        List<Article> all = articlesRepository.findAll();
        if (user != null) {
            List<Article> users = articlesRepository.findAllByAuthorId(user.getId());
            all.removeAll(users);
            initializeArticlesWithBasicInfo(all);
        }
        return all;
    }

    @Override
    public List<Article> getAllArticlesByTag(Tag tag) {
        if (tag != null) {
            List<Article> articleList = articlesRepository.findAllByTagId(tag.getId());
            initializeArticlesWithBasicInfo(articleList);
            return articleList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Tag> getAllTags() {
        return tagsRepository.findAll();
    }

    @Override
    public Set<Tag> getArticleTags(Article article) {
        return tagsRepository.findTagsByArticleId(article.getId());
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
        return articlesRepository.getLikesAmount(article.getId());
    }

    /**
     * Not rearranged comment list. Call rearrangeArticleCommentsList before displaying article
     * @param article
     * @return raw list of article's comments
     */
    @Override
    public List<Comment> getArticleComments(Article article) {
        List<Comment> commentList = commentsRepository.findCommentsByArticleId(article.getId());
        for (Comment comment : commentList) {
            User actualAuthor = usersRepository.findById(comment.getAuthor().getId()).orElse(comment.getAuthor());
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
    public Article getArticleByIdOrSlug(String parameter) {
        // todo: slug search
        Long id = Long.parseLong(parameter);
        return getArticleById(id);
    }

    @Override
    public Tag getTagById(Long tagId) {
        return tagsRepository.findById(tagId).orElse(null);
    }

    private void initializeArticlesWithBasicInfo(List<Article> articleList) {
        for (Article art : articleList) {
            this.initializeArticleWithBasicInfo(art);
        }
    }

    private void initializeArticleWithBasicInfo(Article article) {
        article.setCommentAmount(this.getArticleComments(article).size());
        article.setLikeAmount((int) this.getArticleLikesAmount(article));
        article.setTags(this.getArticleTags(article));
    }
}
