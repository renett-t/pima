package ru.renett.controllers.old.articles;

import ru.renett.exceptions.InvalidRequestDataException;
import ru.renett.service.old.articleService.ArticleSaveDataService;
import ru.renett.configuration.Constants;
import ru.renett.service.old.RequestValidatorInterface;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newComment")
public class CommentAddServlet extends HttpServlet {
    private ArticleSaveDataService articleSaveDataService;
    private RequestValidatorInterface requestValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleSaveDataService = (ArticleSaveDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_SAVE_SERVICE);
        requestValidator = (RequestValidatorInterface) config.getServletContext().getAttribute(Constants.CNTX_REQUEST_VALIDATOR);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {   // validation of id has the same reasons as stated at ArticleLikeServlet class. id sends automatically by form
            int artId = requestValidator.checkRequestedIdCorrect(request.getParameter("id"));
            articleSaveDataService.createComment(request);
            response.sendRedirect(getServletContext().getContextPath() + "/article?id=" + artId);
        } catch (InvalidRequestDataException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
