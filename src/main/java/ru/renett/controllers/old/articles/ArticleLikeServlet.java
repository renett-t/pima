package ru.renett.controllers.old.articles;

import ru.renett.exceptions.InvalidRequestDataException;
import ru.renett.models.Article;
import ru.renett.models.User;
import ru.renett.service.old.articleService.*;
import ru.renett.configuration.Constants;
import ru.renett.service.old.RequestValidatorInterface;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/like")
public class ArticleLikeServlet extends HttpServlet {
    private ArticlesGetDataService articlesGetDataService;
    private ArticleSaveDataService articleSaveDataService;
    private RequestValidatorInterface requestValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articlesGetDataService = (ArticlesGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
        requestValidator = (RequestValidatorInterface) config.getServletContext().getAttribute(Constants.CNTX_REQUEST_VALIDATOR);
        articleSaveDataService = (ArticleSaveDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SAVE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // user likes article just by clicking on the special icon - so the request always should be right, and it will contain
            // valid id value. But what if someone decides to make a request on their own, trying to break all the rules? :O
            Long id = requestValidator.checkRequestedIdCorrect(request.getParameter("id"));
            Article article = articlesGetDataService.getArticleById(id);
            User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);

            if (article != null) {
                if (articlesGetDataService.isArticleLikedByUser(user, article)) {
                    articleSaveDataService.dislikeArticle(user, article);
                } else {
                    articleSaveDataService.likeArticle(user, article);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (InvalidRequestDataException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
