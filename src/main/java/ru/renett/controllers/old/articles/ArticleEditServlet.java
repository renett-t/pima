package ru.renett.controllers.old.articles;

import ru.renett.exceptions.FileUploadException;
import ru.renett.exceptions.InvalidRequestDataException;
import ru.renett.models.Article;
import ru.renett.models.Tag;
import ru.renett.models.User;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.configuration.Constants;
import ru.renett.service.article.ArticleManageDataService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet("/editArticle")
@MultipartConfig
public class ArticleEditServlet extends HttpServlet {
    private ArticlesGetDataService articlesGetDataService;
    private ArticleManageDataService articleManageDataService;
    private RequestValidatorInterface requestValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articlesGetDataService = (ArticlesGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
        articleManageDataService = (ArticleManageDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SAVE_SERVICE);
        requestValidator = (RequestValidatorInterface) config.getServletContext().getAttribute(Constants.CNTX_REQUEST_VALIDATOR);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") != null) {
            try {
                Long idOfRequestedArticle = requestValidator.checkRequestedIdCorrect(request.getParameter("id"));
                Article requestedArticle = articlesGetDataService.getArticleById(idOfRequestedArticle);
                User author = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);

                if (requestedArticle.getAuthor().getId() != author.getId()) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                if (requestedArticle != null) {
                    request.setAttribute("articleInstance", requestedArticle);
                    request.setAttribute("aititle", requestedArticle.getTitle());
                    request.setAttribute("aibody", requestedArticle.getBody());
                } else {
                    request.setAttribute("message", "Извините, но данная статья не была найдена, нечего редактировать. Глядите, что у нас есть для вас:");
                    getServletContext().getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
                    return;
                }
            } catch (InvalidRequestDataException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        List<Tag> tags = articlesGetDataService.getAllTags();
        request.setAttribute("tagList", tags);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/macro_article_edit.ftl.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String submit = request.getParameter("submit");
        System.out.println(submit);
        Long artId = 0L;
        if (submit != null) {
            try {
                if (submit.equals("create")) {
                    artId = articleManageDataService.createArticle(request);
                } else if (submit.equals("edit")) {
                    artId = requestValidator.checkRequestedIdCorrect(request.getParameter("articleId"));
                    articleManageDataService.editArticle(request);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                response.sendRedirect(getServletContext().getContextPath() + "/article?id=" + artId);
            } catch (InvalidRequestDataException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (FileUploadException e) {
                request.setAttribute("message", e.getMessage());
                request.setAttribute("aititle", request.getParameter("title"));
                request.setAttribute("aibody", request.getParameter("articleBody"));
                List<Tag> tags = articlesGetDataService.getAllTags();
                request.setAttribute("tagList", tags);
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/macro_article_edit.ftl.jsp").forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
