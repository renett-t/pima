package ru.renett.controllers.old.profile;

import ru.renett.configuration.Constants;
import ru.renett.models.Article;
import ru.renett.models.User;
import ru.renett.service.old.articleService.ArticlesGetDataService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet("/profile")
public class ProfilePageServlet extends HttpServlet {
    private ArticlesGetDataService articlesGetDataService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articlesGetDataService = (ArticlesGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        List<Article> likedArticlesList = articlesGetDataService.getLikedArticles(user);

        request.setAttribute("user", user);
        request.setAttribute("likedArticlesList", likedArticlesList);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
    }
}
