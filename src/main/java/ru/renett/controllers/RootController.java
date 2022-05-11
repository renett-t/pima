package ru.renett.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.renett.models.Article;
import ru.renett.service.old.articleService.ArticlesGetDataService;

@Controller
@RequestMapping(value = {"/", "/main"})
@RequiredArgsConstructor
public class RootController {

    private final ArticlesGetDataService articlesGetDataService;

    @GetMapping
    public String getMainPage(ModelMap map) {
        // todo: cookie lwai of last viewed article
        Article lastViewed = articlesGetDataService.getArticleById(2L);
        map.put("lwai", lastViewed);

        return "main";
    }

}
