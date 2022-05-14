package ru.renett.controllers.article;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.models.Article;
import ru.renett.models.Tag;
import ru.renett.models.User;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.service.user.UserService;
import ru.renett.utils.TagsCache;

import java.util.ArrayList;
import java.util.List;

import static ru.renett.configuration.Constants.*;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticlesController {
    private final ArticlesGetDataService articlesGetDataService;
    private final UserService userService;

    private final TagsCache tagsCache;

    @GetMapping
    public String getAllArticles(@RequestParam(defaultValue = "", name = "searchTagParam") String searchTagParam,
                              @AuthenticationPrincipal UserDetails userDetails,
                              ModelMap map) {
        User user = null;
        if (userDetails != null) {
            user = userService.getUserByEmailOrUserName(userDetails.getUsername());
            List<Article> owned = articlesGetDataService.getUsersArticles(user);
            map.put(USER_ARTICLES_ATTR, owned);
        }

        List<Article> articles = new ArrayList<>();

        if (!searchTagParam.isEmpty()) {
            if (tagsCache.containsTag(searchTagParam)) {
                Tag tag = tagsCache.getTagByName(searchTagParam);
                articles = articlesGetDataService.getAllArticlesByTag(tag);
                map.put(SEARCH_TAG_ATTR, searchTagParam);
            }
        } else {
            if (user != null)
                articles = articlesGetDataService.getAllArticlesExceptUsers(user);
            else
                articles = articlesGetDataService.getAllArticles();
        }
        map.put(ARTICLES_ATTR, articles);
        return "articles_page";
    }



    @GetMapping("/{article-id}")
    public String getArticleById(@PathVariable("article-id") String parameter,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 ModelMap map) {
        try {
            Article article = articlesGetDataService.getArticleByIdOrSlug(parameter);
            // todo: save cookie of last viewed article
            if (userDetails != null) {
                User user = userService.getUserByEmailOrUserName(userDetails.getUsername());
                if (user.getId().equals(article.getAuthor().getId())) {
                    map.put(OWNED_ATTR, true);
                }

                map.put(LIKED_ATTR,
                        articlesGetDataService.isArticleLikedByUser(user, article));
            }
            map.put(ARTICLES_ATTR, article);
        } catch (ArticleNotFoundException ex) {
            map.put(MESSAGE_ATTR, ex.getMessage()); // todo: message i18n
        }

        return "article_display";
    }
}
