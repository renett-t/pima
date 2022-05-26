package ru.renett.service.article;

import ru.renett.dto.ArticleDto;
import ru.renett.dto.form.ArticleForm;
import ru.renett.dto.form.UpdateArticleForm;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.exceptions.FileUploadException;

public interface ArticlesManageDataService {
    ArticleDto createArticle(ArticleForm form, Long authorId) throws FileUploadException;
    ArticleDto editArticle(UpdateArticleForm form) throws FileUploadException, ArticleNotFoundException;
    void deleteArticle(Long articleId);
    void likeArticle(Long userId, Long articleId);
    void dislikeArticle(Long user, Long articleId);
    Long incrementViewCount(Long articleId);
}
