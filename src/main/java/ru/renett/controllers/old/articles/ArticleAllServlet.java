package ru.renett.controllers.old.articles;

import ru.renett.models.Article;
import ru.renett.models.Tag;
import ru.renett.models.User;
import ru.renett.service.old.articleService.ArticlesGetDataService;
import ru.renett.configuration.Constants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@WebServlet("/articles")
public class ArticleAllServlet extends HttpServlet {
    private ArticlesGetDataService articlesGetDataService;
    private Map<String, Long> mapOfTags;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articlesGetDataService = (ArticlesGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
        mapOfTags = initializeMapOfTags();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        List<Article> list = null;

        String tagParameter = request.getParameter("tag");
        boolean tagRequested = false;

        if (tagParameter != null) {
            if (mapOfTags.containsKey(tagParameter)) {
                Tag searchTag = articlesGetDataService.getTagById(mapOfTags.get(tagParameter));
                list = articlesGetDataService.getAllArticlesByTag(searchTag);
                tagRequested = true;
                request.setAttribute("searchTag", searchTag);
            }
        }

        if (user != null) {
            List<Article> userArticles = articlesGetDataService.getUsersArticles(user);
            request.setAttribute("userArticlesList", userArticles);
            if (!tagRequested) {
                list = articlesGetDataService.getAllArticlesExceptUsers(user);
            }
        } else if (!tagRequested){
            list = articlesGetDataService.getAllArticles();
        }

        request.setAttribute("articlesList", list);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/articles_page.jsp").forward(request, response);
    }

    private Map<String, Long> initializeMapOfTags() {
        Map<String, Long> map = new HashMap<>();
        for (Tag tag: articlesGetDataService.getAllTags()) {
            map.put(tag.getId().toString(), tag.getId());
        }
        map.put("guitar", 3L);
        map.put("music-theory", 9L);
        map.put("songs", 2L);

        return map;
    }
}
