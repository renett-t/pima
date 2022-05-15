package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.renett.configuration.Constants;
import ru.renett.exceptions.FileUploadException;
import ru.renett.models.Article;
import ru.renett.models.Comment;
import ru.renett.models.Tag;
import ru.renett.models.User;
import ru.renett.repository.ArticlesRepository;
import ru.renett.repository.CommentsRepository;
import ru.renett.repository.TagsRepository;
import ru.renett.service.article.ArticleManageDataService;
import ru.renett.utils.HtmlTagsValidator;
import ru.renett.service.file.FileManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleManageDataServiceImpl implements ArticleManageDataService {
    private final String DEFAULT_THUMBNAIL = Constants.DEFAULT_THUMBNAIL;

    private final ArticlesRepository articlesRepository;
    private final CommentsRepository commentsRepository;
    private final TagsRepository tagsRepository;
    private final HtmlTagsValidator htmlTagsValidator;
    private final FileManager fileManager;

    @Override
    public Long createArticle(HttpServletRequest request) {
        String title = request.getParameter("title");
        String body = request.getParameter("articleBody");
        String[] tags = request.getParameterValues("tag");

        User author = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);

        Article newArticle = Article.builder()
                .title(title)
                .body(htmlTagsValidator.checkStringInputTags(body))
                .author(author)
                .tags(new HashSet<>())
                .build();

        Part imagePart = null;
        String imageFileName = null;
        try {
            imagePart = request.getPart("thumbnailImage");
            if (imagePart != null && imagePart.getSize() != 0) {
                imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                try {
                    imageFileName = fileManager.saveFile(imageFileName, request.getServletContext().getRealPath(""), imagePart.getInputStream());
                } catch (FileUploadException e) {
                    throw new FileUploadException(e);
                }
            } else {
                imageFileName = DEFAULT_THUMBNAIL;
            }
        } catch (IOException | ServletException e) {
            throw new FileUploadException("Проблемы с загрузкой изображения", e);
        }

        newArticle.setThumbnailPath(imageFileName);

        if (tags != null) {
            for (String tag : tags) {
                if (!tag.equals("-1")) {
                    newArticle.getTags().add(new Tag(Long.parseLong(tag)));
                }
            }
        }

        articlesRepository.save(newArticle);
        return newArticle.getId();
    }

    @Override
    public void deleteArticle(Article articleToDelete) {
        if (articleToDelete != null) {
            articlesRepository.delete(articleToDelete);
        }
    }

    @Override
    public void editArticle(HttpServletRequest request) {
        String title = request.getParameter("title");
        String body = request.getParameter("articleBody");
        String[] tags = request.getParameterValues("tag");

        User author = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);

        Article editedArticle = Article.builder()
                .id(requestValidator.checkRequestedIdCorrect(request.getParameter("articleId")))
                .title(title)
                .body(htmlTagsValidator.checkStringInputTags(body))
                .author(author)
                .build();

        Part imagePart = null;
        String imageFileName = null;
        try {
            imagePart = request.getPart("thumbnailImage");
            if (imagePart != null && imagePart.getSize() > 0) {
                imageFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                try {
                    imageFileName = fileManager.saveFile(imageFileName, request.getServletContext().getRealPath(""), imagePart.getInputStream());
                } catch (FileUploadException e) {
                    throw new FileUploadException(e);
                }
                editedArticle.setThumbnailPath(imageFileName);
                articlesRepository.save(editedArticle);
            } else {
                articlesRepository.updateWithoutThumbnail(editedArticle);
            }
        } catch (IOException | ServletException e) {
            throw new FileUploadException("Проблемы с загрузкой изображения", e);
        }
        Long articleId = Long.parseLong(request.getParameter("articleId"));
        Set<Tag> newTags = new LinkedHashSet<>();
        Set<Tag> leftTags = new LinkedHashSet<>();
        Set<Tag> oldTags = tagsRepository.findTagsByArticleId(articleId);
        if (tags != null) {
            for (String tag : tags) {
                if (!tag.equals("-1")) {
                    newTags.add(new Tag(Long.parseLong(tag)));
                    leftTags.add(new Tag(Long.parseLong(tag)));
                }
            }
        }

        newTags.removeAll(oldTags);
        oldTags.removeAll(leftTags);
//        todo: fix saving tags (
//        tagRepository.save()
//        tagRepository.saveNewTags(newTags, articleId);
//        tagRepository.deleteOldTags(oldTags, articleId);
    }

    @Override
    public void likeArticle(User user, Article likedArticle) {
        if (likedArticle != null && user != null) {
            articlesRepository.updateLikesAmount(user.getId(), likedArticle.getId());
        }
    }

    @Override
    public void dislikeArticle(User user, Article dislikedArticle) {
        if (user != null && dislikedArticle != null) {
            articlesRepository.removeLikeFromArticle(user.getId(), dislikedArticle.getId());
        }
    }

    @Override
    public void updateViewCount(Article article) {
        articlesRepository.updateViewCount(article.getId(), article.getViewAmount());
    }

 }
