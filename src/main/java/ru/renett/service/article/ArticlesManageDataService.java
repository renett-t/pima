package ru.renett.service.article;

import ru.renett.dto.ArticleDto;
import ru.renett.dto.form.ArticleForm;
import ru.renett.dto.form.UpdateArticleForm;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.exceptions.DuplicateParameterException;
import ru.renett.exceptions.FileUploadException;

public interface ArticlesManageDataService {
    ArticleDto createArticle(ArticleForm form, Long authorId) throws FileUploadException, DuplicateParameterException;
    ArticleDto editArticle(UpdateArticleForm form) throws ArticleNotFoundException, FileUploadException, DuplicateParameterException;
    void deleteArticle(Long articleId);
    boolean likeArticle(Long userId, Long articleId); // возвращает true - если был поставлен лайк, false - лайк удалён
    void dislikeArticle(Long user, Long articleId);
    Long incrementViewCount(Long articleId);
}
