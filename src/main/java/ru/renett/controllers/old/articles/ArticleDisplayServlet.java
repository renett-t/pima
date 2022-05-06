package ru.renett.controllers.old.articles;

import ru.renett.exceptions.InvalidRequestDataException;
import ru.renett.models.Article;
import ru.renett.models.User;
import ru.renett.service.old.articleService.ArticleGetDataService;
import ru.renett.configuration.Constants;
import ru.renett.service.old.articleService.ArticleSaveDataService;
import ru.renett.service.old.RequestValidatorInterface;
import ru.renett.service.old.userService.UserPreferencesInterface;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/article")
public class ArticleDisplayServlet extends HttpServlet {
    private ArticleGetDataService articleGetDataService;
    private ArticleSaveDataService articleSaveDataService;
    private RequestValidatorInterface requestValidator;
    private UserPreferencesInterface preferencesManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleGetDataService = (ArticleGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
        requestValidator = (RequestValidatorInterface) config.getServletContext().getAttribute(Constants.CNTX_REQUEST_VALIDATOR);
        preferencesManager = (UserPreferencesInterface) config.getServletContext().getAttribute(Constants.CNTX_PREFERENCES_MANAGER);
        articleSaveDataService = (ArticleSaveDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SAVE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long idOfRequestedArticle = requestValidator.checkRequestedIdCorrect(request.getParameter("id"));
            Article requestedArticle = articleGetDataService.getArticleById(idOfRequestedArticle);

            if (requestedArticle != null) {
                preferencesManager.saveLastViewedArticleIdCookie(requestedArticle.getId(), response);

                User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
                if (user != null) {
                    if (user.getId() == requestedArticle.getAuthor().getId()) {
                        request.setAttribute("author", user);
                    }
                    if (articleGetDataService.isArticleLikedByUser(user, requestedArticle)) {
                        request.setAttribute("liked", true);
                    }
                }
                requestedArticle.setViewAmount(requestedArticle.getViewAmount() + 1);
                articleSaveDataService.updateViewCount(requestedArticle);
                request.setAttribute("articleInstance", requestedArticle);
            } else {
                request.setAttribute("message", "Извините, но данная статья не была найдена. ");
            }
        } catch (InvalidRequestDataException e) {
            request.setAttribute("message", "Извините, но данная статья не была найдена. ");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article_display.jsp").forward(request, response);
    }
}
