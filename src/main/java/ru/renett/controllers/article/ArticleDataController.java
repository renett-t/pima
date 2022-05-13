package ru.renett.controllers.article;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.renett.dto.AddArticleDto;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.models.Article;
import ru.renett.models.User;
import ru.renett.service.old.articleService.ArticleManageDataService;
import ru.renett.service.old.articleService.ArticlesGetDataService;
import ru.renett.service.user.UserService;
import ru.renett.utils.TagsCache;

import static ru.renett.configuration.Constants.MESSAGE_ATTR;
import static ru.renett.configuration.Constants.TAGS_ATTR;

@Controller
@RequiredArgsConstructor
public class ArticleDataController {
    private final ArticlesGetDataService articlesGetDataService;
    private final ArticleManageDataService articleManageDataService;
    private final UserService userService;
    private final TagsCache tagsCache;

    // todo
    @GetMapping("/articles/{article-id}/edit")
    public String editArticle(@PathVariable("article-id") String parameter) {

        return "article_edit";
    }

    @PostMapping("/articles/{article-id}/like")
    public String likeArticle(@PathVariable("article-id") String parameter,
                              @AuthenticationPrincipal UserDetails userDetails,
                              ModelMap map) {
        try {
            Article article = articlesGetDataService.getArticleByIdOrSlug(parameter);
            if (userDetails == null) {
                return "redirect:/signIn";
            } else {
                User user = userService.getUserByEmailOrUserName(userDetails.getUsername());
                articleManageDataService.likeArticle(user, article);
            }
        } catch (ArticleNotFoundException ex) {
            map.put(MESSAGE_ATTR, "Article that you requested to like do not exist. Redirected here :)"); // todo: i18n
        }
        return "redirect:/article_display";
    }

    @PostMapping("/articles/{article-id}/delete")
    public String deleteArticle(@PathVariable("article-id") String parameter, ModelMap map) {
        try {
            Article articleToDelete = articlesGetDataService.getArticleByIdOrSlug(parameter);
            articleManageDataService.deleteArticle(articleToDelete);
        } catch (ArticleNotFoundException ex) {
            map.put(MESSAGE_ATTR, "Article that you requested to delete do not exist. Redirected here :)"); // todo: i18n
        }

        return "redirect:/articles";
    }

    //todo... think of ArticleDto
    @GetMapping("/articles/new")
    public String createNewArticle(ModelMap map) {
        map.put("articleDto", new AddArticleDto());
        map.put(TAGS_ATTR, articlesGetDataService.getAllTags());

        return "article_edit";
    }


}
