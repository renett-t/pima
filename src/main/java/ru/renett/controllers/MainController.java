package ru.renett.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.renett.configuration.Constants;
import ru.renett.dto.ArticleDto;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.service.article.ArticlesGetDataService;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = {"/", "/main"})
@RequiredArgsConstructor
public class MainController {

    private final ArticlesGetDataService articlesGetDataService;

    @GetMapping
    public String getMainPage(HttpServletRequest request, @CookieValue(value = Constants.COOKIE_LAST_VIEWED_ARTICLE, defaultValue = "-1") String lwai, ModelMap map) {
        if (lwai != null && !lwai.equals("-1")) {
            try {
                ArticleDto lastViewed = articlesGetDataService.getArticleByIdOrSlug(lwai);
                map.put("lwai", lastViewed);
            } catch (EntityNotFoundException ignored) {
            }
        }

        return "main";
    }

}
