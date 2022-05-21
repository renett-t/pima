package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.renett.configuration.Constants;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.TagDto;
import ru.renett.dto.form.ArticleForm;
import ru.renett.dto.form.UpdateArticleForm;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.exceptions.FileUploadException;
import ru.renett.models.Article;
import ru.renett.models.Like;
import ru.renett.models.Tag;
import ru.renett.models.User;
import ru.renett.repository.*;
import ru.renett.service.article.ArticlesManageDataService;
import ru.renett.service.file.FileManager;
import ru.renett.utils.HtmlTagsValidator;
import ru.renett.utils.TagsCache;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticlesManageDataServiceImpl implements ArticlesManageDataService {

    private final static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final String DEFAULT_THUMBNAIL = Constants.DEFAULT_THUMBNAIL;

    private final UsersRepository usersRepository;
    private final ArticlesRepository articlesRepository;
    private final CommentsRepository commentsRepository;
    private final LikesRepository likesRepository;
    private final TagsRepository tagsRepository;
    private final TagsCache tagsCache;
    private final HtmlTagsValidator htmlTagsValidator;
    private final FileManager fileManager;

    @Override
    public ArticleDto createArticle(ArticleForm form, Long authorId) throws FileUploadException {
        logger.info("Creating new article. Data: user id =" + authorId + ", contents = " + form.toString() + ".");
        Article newArticle = Article.builder()
                .title(form.getTitle())
                .body(htmlTagsValidator.checkStringInputTags(form.getBody()))
                .author(new User(authorId)) // todo: check if it's ok or i need to go to db to get user
                .tags(new HashSet<>())
                .build();

        MultipartFile imagePart = null;
        String imageFileName = null;
        try {
            imagePart = form.getThumbnailImage();
            if (imagePart != null && imagePart.getSize() != 0) {
                imageFileName = Paths.get(imagePart.getOriginalFilename()).getFileName().toString();
                try {
                    imageFileName = fileManager.saveFile(imageFileName, imagePart.getInputStream());
                } catch (FileUploadException reThrow) {
                    throw reThrow;
                }
            } else {
                imageFileName = DEFAULT_THUMBNAIL;
            }
        } catch (IOException e) {
            logger.warn("Unable to save image from article create request. Exception " + e.getClass() + " happened. Message:" + e.getMessage());
            throw new FileUploadException("Проблемы с загрузкой изображения", e);
        }

        newArticle.setThumbnailPath(imageFileName);

        if (form.getTags() != null) {
            for (String tag : form.getTags()) {
                if (!tag.equals("-1")) {
                    TagDto tagDto = tagsCache.getTagByName(tag);
                    newArticle.getTags().add(
                            Tag.builder()
                            .id(tagDto.getId())
                            .title(tagDto.getTitle())
                            .build()
                    );
                } else {
                    newArticle.setTags(new HashSet<>());
                    break;
                }
            }
        }

        articlesRepository.save(newArticle);
        return ArticleDto.from(newArticle);
    }

    @Override
    public ArticleDto editArticle(UpdateArticleForm form) throws FileUploadException {
        Article editedArticle = articlesRepository.findById(form.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("Article with id = " + form.getArticleId() + "not found. Unable to edit."));
        editedArticle.setTitle(form.getTitle());
        editedArticle.setBody(form.getBody());

        MultipartFile imagePart = null;
        String imageFileName = null;
        try {
            imagePart = form.getThumbnailImage();
            if (imagePart != null && imagePart.getSize() > 0) {
                imageFileName = Paths.get(imagePart.getOriginalFilename()).getFileName().toString();
                try {
                    imageFileName = fileManager.saveFile(imageFileName, imagePart.getInputStream());
                } catch (FileUploadException e) {
                    throw new FileUploadException(e);
                }
                editedArticle.setThumbnailPath(imageFileName);
                articlesRepository.save(editedArticle);
            } else {
                articlesRepository.updateWithoutThumbnail(editedArticle);
            }
        } catch (IOException e) {
            throw new FileUploadException("Проблемы с загрузкой изображения", e);
        }

        Set<Tag> newTags = new LinkedHashSet<>();
        Set<Tag> leftTags = new LinkedHashSet<>();
        Set<Tag> oldTags = tagsRepository.findTagsByArticleId(editedArticle.getId());

        boolean isBreaked = false;
        if (form.getTags() != null) {
            for (String tag : form.getTags()) {
                if (!tag.equals("-1")) {
                    TagDto tagDto = tagsCache.getTagByName(tag);
                    Tag real = Tag.builder()
                                    .id(tagDto.getId())
                                    .title(tagDto.getTitle())
                                    .build();
                    newTags.add(real);
                    leftTags.add(real);
                } else {
                    editedArticle.setTags(new HashSet<>());
                    isBreaked = true;
                    break;
                }
            }
        }

        newTags.removeAll(oldTags);
        oldTags.removeAll(leftTags);
        if (!isBreaked) {
            editedArticle.setTags(newTags);
        }
//        todo: fix saving tags =(
//        tagRepository.save()
//        tagRepository.saveNewTags(newTags, articleId);
//        tagRepository.deleteOldTags(oldTags, articleId);

        articlesRepository.save(editedArticle);
        return ArticleDto.from(editedArticle);
    }

    @Override
    public void deleteArticle(Long articleId) {
        articlesRepository.deleteById(articleId);
    }

    @Override
    public void likeArticle(Long userId, Long articleId) {
        Optional<Like> like = likesRepository.findByUserIdAndArticleId(userId, articleId);
        if (like.isPresent()) {
            likesRepository.delete(like.get());
        } else {
//            Like newLike = Like.builder()
//                    .user(usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id = " + userId + " not found")))
//                    .article(articlesRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("Article with id = " + articleId + " not found")))
//                    .build();
//            likesRepository.save(newLike);
            articlesRepository.updateLikesAmount(userId, articleId);
        }
    }

    @Override
    public void dislikeArticle(Long userId, Long articleId) {
        Optional<Like> like = likesRepository.findByUserIdAndArticleId(userId, articleId);
        if (like.isPresent()) {
            likesRepository.delete(like.get());
            articlesRepository.removeLikeFromArticle(userId, articleId);
        } else {
            // thinking
        }
    }

    @Override
    public void incrementViewCount(Long articleId) {
        Article article = articlesRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article with id = " + articleId + " not found"));

        articlesRepository.updateViewCount(articleId, article.getViewAmount() + 1);
    }
}
