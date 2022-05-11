package ru.renett.controllers.old.articles;

import ru.renett.exceptions.InvalidRequestDataException;
import ru.renett.models.Article;
import ru.renett.service.old.articleService.ArticlesGetDataService;
import ru.renett.configuration.Constants;
import ru.renett.service.old.articleService.ArticleSaveDataService;
import ru.renett.service.old.RequestValidatorInterface;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/deleteArticle")
public class ArticleDeleteServlet extends HttpServlet {
    private ArticlesGetDataService articlesGetDataService;
    private ArticleSaveDataService articleSaveDataService;
    private RequestValidatorInterface requestValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articlesGetDataService = (ArticlesGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
        articleSaveDataService = (ArticleSaveDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SAVE_SERVICE);
        requestValidator = (RequestValidatorInterface) config.getServletContext().getAttribute(Constants.CNTX_REQUEST_VALIDATOR);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = requestValidator.checkRequestedIdCorrect(request.getParameter("id"));
            Article articleToDelete = articlesGetDataService.getArticleById(id);
            if (articleToDelete != null) {
                articleSaveDataService.deleteArticle(articleToDelete);
                response.sendRedirect(getServletContext().getContextPath() + "/articles");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (InvalidRequestDataException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
