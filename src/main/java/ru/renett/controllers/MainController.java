package ru.renett.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.renett.configuration.Constants;
import ru.renett.dto.ArticleDto;
import ru.renett.models.Article;
import ru.renett.service.article.ArticlesGetDataService;

@Controller
@RequestMapping(value = {"/", "/main"})
@RequiredArgsConstructor
public class MainController {

    private final ArticlesGetDataService articlesGetDataService;

    @GetMapping
    public String getMainPage(@CookieValue(value = Constants.COOKIE_LAST_VIEWED_ARTICLE, required = false) String lwai, ModelMap map) {
        if (lwai != null) {
            ArticleDto lastViewed = articlesGetDataService.getArticleByIdOrSlug(lwai);
            map.put("lwai", lastViewed);
        }

        return "main";
    }

}
