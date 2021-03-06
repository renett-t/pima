package ru.renett.controllers.article;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.TagDto;
import ru.renett.dto.UserDto;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.service.article.ArticlesManageDataService;
import ru.renett.service.user.UserPreferencesService;
import ru.renett.service.user.UsersService;
import ru.renett.utils.TagsCache;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static ru.renett.configuration.Constants.*;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticlesController {
    private final ArticlesGetDataService articlesGetDataService;
    private final ArticlesManageDataService articlesManageDataService;
    private final UserPreferencesService userPreferencesService;
    private final UsersService usersService;

    private final TagsCache tagsCache;

    private final MessageSource messageSource;

    @GetMapping
    public String getAllArticles(@RequestParam(defaultValue = "", name = "tag") String searchTagParam,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 ModelMap map) {
        UserDto user = null;
        if (userDetails != null) {
            user = usersService.getUserByEmailOrUserName(userDetails.getUsername());
            List<ArticleDto> owned = articlesGetDataService.getUsersArticles(user.getId());
            map.put(USER_ARTICLES_ATTR, owned);
        }

        List<ArticleDto> articles = new ArrayList<>();

        if (!searchTagParam.isEmpty()) {
            String param = getSearchTagName(searchTagParam);
            if (tagsCache.containsTag(param)) {
                TagDto tag = tagsCache.getTagByName(param);
                articles = articlesGetDataService.getAllArticlesByTag(tag.getId());
                map.put(SEARCH_TAG_ATTR, tag);
            } else {
                map.put(MESSAGE_ATTR, messageSource.getMessage("page.articles.by_tag.not_found", null, LocaleContextHolder.getLocale()));
            }
        } else {
            if (user != null)
                articles = articlesGetDataService.getAllArticlesExceptUsers(user.getId());
            else
                articles = articlesGetDataService.getAllArticles();
        }
        map.put(ARTICLES_ATTR, articles);
        return "articles_page";
    }

    private String getSearchTagName(String searchTagParam) {
        switch (searchTagParam) {
            case "guitar":
                return "????????????";
            case "music-theory":
                return "???????????? ????????????";
            case "songs":
                return "???????????? ??????????";
            default:
                return searchTagParam;
        }
    }

    @GetMapping("/{article-id}")
    public String getArticleById(@PathVariable("article-id") String parameter,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 HttpServletResponse response,
                                 ModelMap map) {
        try {
            ArticleDto article = articlesGetDataService.getArticleByIdOrSlug(parameter);
            userPreferencesService.saveLastViewedArticleIdCookie(article.getId(), response);

            if (userDetails != null) {
                UserDto user = usersService.getUserByEmailOrUserName(userDetails.getUsername());
                if (user.getId().equals(article.getAuthor().getId())) {
                    map.put(OWNED_ATTR, true);
                }

                map.put(LIKED_ATTR,
                        articlesGetDataService.isArticleLikedByUser(user.getId(), article.getId()));
            }

            article.setViews(Math.toIntExact(articlesManageDataService.incrementViewCount(article.getId())));
            map.put(ARTICLE_ATTR, article);
        } catch (EntityNotFoundException rethrow) {
            throw rethrow;
//            map.put(MESSAGE_ATTR, messageSource.getMessage("page.articles.not_found", null, LocaleContextHolder.getLocale()));
        }

        return "article_display";
    }
}
