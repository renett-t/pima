package ru.kpfu.itis.renett.servlets;

import ru.kpfu.itis.renett.exceptions.InvalidRequestDataException;
import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.service.Constants;
import ru.kpfu.itis.renett.service.articleService.ArticleGetDataService;
import ru.kpfu.itis.renett.service.security.RequestValidatorInterface;
import ru.kpfu.itis.renett.service.userService.UserPreferencesInterface;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {
    private ArticleGetDataService articleGetDataService;
    private UserPreferencesInterface userPreferencesManager;
    private RequestValidatorInterface requestValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleGetDataService = (ArticleGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
        userPreferencesManager = (UserPreferencesInterface) config.getServletContext().getAttribute(Constants.CNTX_PREFERENCES_MANAGER);
        requestValidator = (RequestValidatorInterface) config.getServletContext().getAttribute(Constants.CNTX_REQUEST_VALIDATOR);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Cookie lwai = userPreferencesManager.getCookieOfLastViewedArticle(request);
            Article lastViewedArticle = articleGetDataService.getArticleById(requestValidator.checkRequestedIdCorrect(lwai.getValue()));
            request.setAttribute("lwai", lastViewedArticle);
        } catch (InvalidRequestDataException ignored) {
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
    }
}
