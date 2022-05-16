package ru.renett.controllers.article;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.UserDto;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.models.User;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.service.article.ArticlesManageDataService;
import ru.renett.service.user.UsersService;

import static ru.renett.configuration.Constants.MESSAGE_ATTR;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final ArticlesGetDataService articlesGetDataService;
    private final ArticlesManageDataService articlesManageDataService;
    private final UsersService usersService;

    @PostMapping("/articles/{article-id}/like")
    public String likeArticle(@PathVariable("article-id") String parameter,
                              @AuthenticationPrincipal UserDetails userDetails,
                              ModelMap map) {
        try {
            ArticleDto article = articlesGetDataService.getArticleByIdOrSlug(parameter);
            if (userDetails == null) {
                return "redirect:/signIn";
            } else {
                UserDto user = usersService.getUserByEmailOrUserName(userDetails.getUsername());
                articlesManageDataService.likeArticle(user.getId(), article.getId());
            }
        } catch (ArticleNotFoundException ex) {
            map.put(MESSAGE_ATTR, "Article that you requested to like do not exist. Redirected here :)"); // todo: i18n
        }
        return "redirect:/article_display";
    }
}
