package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.TagDto;
import ru.renett.dto.form.ArticleForm;
import ru.renett.dto.form.UpdateArticleForm;
import ru.renett.exceptions.*;
import ru.renett.models.Article;
import ru.renett.models.Like;
import ru.renett.models.Tag;
import ru.renett.models.User;
import ru.renett.repository.*;
import ru.renett.service.article.ArticlesManageDataService;
import ru.renett.service.file.FileManager;
import ru.renett.utils.HtmlTagsValidator;
import ru.renett.utils.TagsCache;

import java.sql.SQLException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticlesManageDataServiceImpl implements ArticlesManageDataService {

    private final static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;
    private final ArticlesRepository articlesRepository;
    private final LikesRepository likesRepository;
    private final TagsCache tagsCache;
    private final HtmlTagsValidator htmlTagsValidator;
    private final FileManager fileManager;

    @Override
    public ArticleDto createArticle(ArticleForm form, Long authorId) throws FileUploadException {
        logger.info("Creating new article. Data: author id =" + authorId + ", contents = " + form.toString() + ".");
        Article newArticle = Article.builder().title(form.getTitle()).body(htmlTagsValidator.checkStringInputTags(form.getBody())).author(new User(authorId)).tags(new HashSet<>()).likes(new HashSet<>()).commentList(new ArrayList<>()).publishedAt(new Date()).viewAmount(0L).build();

        String imageFileName = this.saveFile(form.getThumbnailImage());

        newArticle.setThumbnailPath(imageFileName);
        newArticle.setTags(new HashSet<>());

        if (form.getTags() != null) {
            for (String tag : form.getTags()) {
                if (!tag.equals("-1")) {
                    TagDto tagDto = tagsCache.getTagById(tag);
                    newArticle.getTags().add(Tag.builder().id(tagDto.getId()).title(tagDto.getTitle()).build());
                } else {
                    newArticle.setTags(new HashSet<>());
                    break;
                }
            }
        }

        newArticle = this.save(newArticle);
        return ArticleDto.from(newArticle);
    }

    private String saveFile(MultipartFile thumbnailImage) throws FileUploadException {
        try {
            return fileManager.saveFile(thumbnailImage);
        } catch (FileUploadException reThrow) {
            logger.warn("Unable to save image from article create request. Exception " + reThrow.getClass() + " happened. Message:" + reThrow.getMessage());
            throw reThrow;
        }
    }

    @Override
    public ArticleDto editArticle(UpdateArticleForm form) throws FileUploadException {
        Article editedArticle = articlesRepository.findById(form.getArticleId()).orElseThrow(() -> new ArticleNotFoundException("Article with id = " + form.getArticleId() + "not found. Unable to edit."));
        editedArticle.setTitle(form.getTitle());
        editedArticle.setBody(form.getBody());

        if (!form.getThumbnailImage().isEmpty()) {
            editedArticle.setThumbnailPath(
                    fileManager.saveFile(form.getThumbnailImage()));
        }

        Set<Tag> newTags = new LinkedHashSet<>();
        if (form.getTags() != null) {
            for (String tag : form.getTags()) {
                if (!tag.equals("-1")) {
                    TagDto tagDto = tagsCache.getTagById(tag);
                    Tag real = Tag.builder().id(tagDto.getId()).title(tagDto.getTitle()).build();
                    newTags.add(real);
                } else {
                    newTags = new HashSet<>();
                    break;
                }
            }
        }
        editedArticle.setTags(newTags);

        return ArticleDto.from(save(editedArticle));
    }

    private Article save(Article entity) throws DuplicateParameterException, ServiceException {
        try {
            return articlesRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new DuplicateParameterException(e.getMessage());
                } else {
                    logger.error("Some error saving new article to db. Cause = " + rootCause);
                    throw new ServiceException("...");
                }
            } else {
                logger.error("Some error saving new article to db. Cause = " + e);
                throw new ServiceException("...");
            }
        }
    }

    @Override
    public void deleteArticle(Long articleId) {
        logger.info("Deleting article with id = " + articleId);
        articlesRepository.deleteById(articleId);
    }

    @Override
    public boolean likeArticle(Long userId, Long articleId) {
        Optional<Like> like = likesRepository.findByUserIdAndArticleId(userId, articleId);
        if (like.isPresent()) {
            likesRepository.deleteById(like.get().getId());
            return false;
        } else {
//            Like newLike = Like.builder()
//                    .user(usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id = " + userId + " not found")))
//                    .article(articlesRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("Article with id = " + articleId + " not found")))
//                    .build();
            Like newLike = Like.builder().user(new User(userId)).article(new Article(articleId)).build();
            likesRepository.save(newLike);
            return true;
        }
    }

    @Override
    public void dislikeArticle(Long userId, Long articleId) {
        Optional<Like> like = likesRepository.findByUserIdAndArticleId(userId, articleId);
        like.ifPresent(likesRepository::delete);
    }

    @Override
    public Long incrementViewCount(Long articleId) {
        Article article = articlesRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("Article with id = " + articleId + " not found"));

        // почему-то этот способ не прокатывает(
        // articlesRepository.updateViewCount(articleId, article.getViewAmount() + 1);
        article.setViewAmount(article.getViewAmount() + 1);
        return articlesRepository.save(article).getViewAmount();
    }
}
