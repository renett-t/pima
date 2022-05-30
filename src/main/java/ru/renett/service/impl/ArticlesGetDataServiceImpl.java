package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.CommentDto;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.models.*;
import ru.renett.repository.*;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.utils.CommentsRearranger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticlesGetDataServiceImpl implements ArticlesGetDataService {
    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;
    private final CommentsRepository commentsRepository;
    private final LikesRepository likesRepository;
    private final CommentsRearranger commentsRearranger;
    private final TagsRepository tagsRepository;

    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = articlesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article with id = " + id + " not found."));

        initializeArticleWithBasicInfo(article);

        List<Comment> comments = commentsRearranger.rearrangeCommentsList(new ArrayList(article.getCommentList()));
        ArticleDto articleDto = ArticleDto.from(article);
        articleDto.setComments(CommentDto.from(comments));
        return articleDto;
    }

    @Override
    public List<ArticleDto> getUsersArticles(Long userId) {
        Optional<User> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            List<Article> articleList = articlesRepository.findAllByAuthorId(userId);
            initializeArticlesWithBasicInfo(articleList);
            return ArticleDto.from(articleList);
        } else {
//            throw new EntityNotFoundException("User with id = " + userId + " not found.");
            return new ArrayList<ArticleDto>();
        }
    }

    @Override
    public List<ArticleDto> getLikedArticles(Long userId) {
        Optional<User> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            List<Article> articleList = articlesRepository.findAllLikedArticles(userId);
            initializeArticlesWithBasicInfo(articleList);
            return ArticleDto.from(articleList);
        }
//        throw new EntityNotFoundException("User with id = " + userId + " not found.");
        return new ArrayList<ArticleDto>();
    }

    @Override
    public List<ArticleDto> getAllArticles() {
        List<Article> articleList = articlesRepository.findAll();
        initializeArticlesWithBasicInfo(articleList);
        return ArticleDto.from(articleList);
    }

    @Override
    public List<ArticleDto> getAllArticlesExceptUsers(Long userId) {
        List<Article> all = articlesRepository.findAll();
        Optional<User> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            List<Article> users = articlesRepository.findAllByAuthorId(userId);
            all.removeAll(users);
        }
        initializeArticlesWithBasicInfo(all);
        return ArticleDto.from(all);
    }

    @Override
    public List<ArticleDto> getAllArticlesByTag(Long tagId) {
        Optional<Tag> tag = tagsRepository.findById(tagId);
        if (tag.isPresent()) {
            List<Article> articleList = articlesRepository.findAllByTagId(tagId);
            initializeArticlesWithBasicInfo(articleList);
            return ArticleDto.from(articleList);
        }
        //        throw new EntityNotFoundException("Tag with id = " + tagId + " not found.");
        return new ArrayList<>();
    }

    @Override
    public boolean isArticleLikedByUser(Long userId, Long articleId) {
        Optional<User> user = usersRepository.findById(userId);
        Optional<Article> article = articlesRepository.findById(articleId);
        if (user.isPresent() && article.isPresent()) {
            Optional<Like> like = likesRepository.findByUserIdAndArticleId(userId, articleId);
            return like.isPresent();
        } else {
            throw new EntityNotFoundException("Something wrong with userId or articleId");
        }
    }

    @Override
    public int getArticleLikesAmount(Long articleId) {
        Optional<Article> article = articlesRepository.findById(articleId);
        if (article.isPresent()) {
            List<Like> likes = likesRepository.findAllByArticleId(articleId);
            return likes.size();
        } else {
            throw new EntityNotFoundException("Article with id " + articleId + "not found.");
        }
    }

    @Override
    public ArticleDto getArticleByIdOrSlug(String parameter) {
        try {
            Long id = Long.parseLong(parameter);
            return getArticleById(id);
        } catch (NumberFormatException e) {
            throw new ArticleNotFoundException("Wrong parameter.");
        }
    }

    private void initializeArticlesWithBasicInfo(List<Article> articleList) {
        for (Article article : articleList) {
            initializeArticleWithBasicInfo(article);
        }
    }

    private void initializeArticleWithBasicInfo(Article article) {
        article.setCommentAmount(article.getCommentList().size());
        article.setLikeAmount(this.getArticleLikesAmount(article.getId()));
    }
}
