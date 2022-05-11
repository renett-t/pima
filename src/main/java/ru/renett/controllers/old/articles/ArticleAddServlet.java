package ru.renett.controllers.old.articles;

import ru.renett.models.Tag;
import ru.renett.service.old.articleService.ArticlesGetDataService;
import ru.renett.configuration.Constants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet("/newArticle")
@MultipartConfig
public class ArticleAddServlet extends HttpServlet {
    private ArticlesGetDataService articlesGetDataService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articlesGetDataService = (ArticlesGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tag> tags = articlesGetDataService.getAllTags();
        request.setAttribute("tagList", tags);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/article_edit.jsp").forward(request, response);
    }

}
