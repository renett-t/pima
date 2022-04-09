package ru.kpfu.itis.renett.servlets.articles;

import ru.kpfu.itis.renett.models.Article;
import ru.kpfu.itis.renett.models.Tag;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.service.articleService.ArticleGetDataService;
import ru.kpfu.itis.renett.service.Constants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/articles")
public class ArticleAllServlet extends HttpServlet {
    private ArticleGetDataService articleGetDataService;
    private Map<String, Integer> mapOfTags;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        articleGetDataService = (ArticleGetDataService) config.getServletContext().getAttribute(Constants.CNTX_ARTICLE_GET_SERVICE);
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
                Tag searchTag = articleGetDataService.getTagById(mapOfTags.get(tagParameter));
                list = articleGetDataService.getAllArticlesByTag(searchTag);
                tagRequested = true;
                request.setAttribute("searchTag", searchTag);
            }
        }

        if (user != null) {
            List<Article> userArticles = articleGetDataService.getUsersArticles(user);
            request.setAttribute("userArticlesList", userArticles);
            if (!tagRequested) {
                list = articleGetDataService.getAllArticlesExceptUsers(user);
            }
        } else if (!tagRequested){
            list = articleGetDataService.getAllArticles();
        }

        request.setAttribute("articlesList", list);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/articles_page.jsp").forward(request, response);
    }

    private Map<String, Integer> initializeMapOfTags() {
        Map<String, Integer> map = new HashMap<>();
        for (Tag tag: articleGetDataService.getAllTags()) {
            map.put(tag.getId().toString(), tag.getId());
        }
        map.put("guitar", 3);
        map.put("music-theory", 9);
        map.put("songs", 2);

        return map;
    }
}
