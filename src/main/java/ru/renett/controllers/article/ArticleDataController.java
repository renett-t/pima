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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.TagDto;
import ru.renett.dto.UserDto;
import ru.renett.dto.form.ArticleForm;
import ru.renett.dto.form.UpdateArticleForm;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.exceptions.FileUploadException;
import ru.renett.models.User;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.service.article.ArticlesManageDataService;
import ru.renett.service.user.UsersService;
import ru.renett.utils.TagsCache;

import java.util.HashSet;
import java.util.List;

import static ru.renett.configuration.Constants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleDataController {
    private final ArticlesGetDataService articlesGetDataService;
    private final ArticlesManageDataService articlesManageDataService;
    private final TagsCache tagsCache;
    private final UsersService usersService;

    private final MessageSource messageSource;

    @GetMapping("/new")
    public String getCreateArticlePage(ModelMap map) {
        List<TagDto> tagDtos = tagsCache.getTags();

        map.put(ARTICLE_ATTR, ArticleDto.builder().title("").body("").build());
        map.put(TAGS_ATTR, tagDtos);

        return "article_edit";
    }

    @PostMapping("/new")
    public String createArticle(@AuthenticationPrincipal UserDetails userDetails,
                                ArticleForm form,
                                ModelMap map) {
        System.out.println("--------------- NEW ARTICLE _____________________");
        System.out.println(form);
        UserDto user = usersService.getUserByEmailOrUserName(userDetails.getUsername());
        ArticleDto articleDto = null;
        try {
            articleDto = articlesManageDataService.createArticle(form, user.getId());
        } catch (FileUploadException e) {
            map.put(MESSAGE_ATTR, messageSource.getMessage("error.file_upload", null, LocaleContextHolder.getLocale()));

            map.put(ARTICLE_ATTR, ArticleDto.builder()
                    .body(form.getBody())
                    .title(form.getTitle())
                    .build());
            map.put(TAGS_ATTR, tagsCache.getTags());

            return "article_edit";
        }

        System.out.println(MvcUriComponentsBuilder.fromMappingName("AC#getArticleById").arg(0, articleDto.getId()).build());
        return "redirect:/" + MvcUriComponentsBuilder.fromMappingName("AC#getArticleById").arg(0, articleDto.getId()).build();
    }

    @GetMapping("/{article-id}/edit")
    public String getEditArticlePage(@PathVariable("article-id") String parameter, ModelMap map) {
        ArticleDto articleDto = articlesGetDataService.getArticleByIdOrSlug(parameter);
        List<TagDto> tagDtos = tagsCache.getTags();
        map.put(ARTICLE_ATTR, articleDto);
        map.put(TAGS_ATTR, tagDtos);

        return "article_edit";
    }

    @PostMapping("/{article-id}/edit")
    public String editArticle(@PathVariable("article-id") String parameter,
                              @AuthenticationPrincipal UserDetails userDetails,
                              UpdateArticleForm form,
                              ModelMap map) {
        System.out.println("--------------- EDIT ARTICLE _____________________");
        System.out.println(form);
        ArticleDto articleDto = null;
        try {
            articleDto = articlesManageDataService.editArticle(form);
        } catch (FileUploadException e) {
            map.put(MESSAGE_ATTR, messageSource.getMessage("error.file_upload", null, LocaleContextHolder.getLocale()));

            articleDto = articlesGetDataService.getArticleByIdOrSlug(parameter);
            articleDto.setTitle(form.getTitle());
            articleDto.setBody(form.getBody());
            List<TagDto> tagDtos = tagsCache.getTags();
            map.put(ARTICLE_ATTR, articleDto);
            map.put(TAGS_ATTR, tagDtos);

            return "article_edit";
        }

        System.out.println(MvcUriComponentsBuilder.fromMappingName("AC#getArticleById").arg(0, articleDto.getId()).build());
        return "redirect:/" + MvcUriComponentsBuilder.fromMappingName("AC#getArticleById").arg(0, articleDto.getId()).build();
    }

    @PostMapping("/{article-id}/delete")
    public String deleteArticle(@PathVariable("article-id") String parameter, ModelMap map) {
        try {
            ArticleDto articleToDelete = articlesGetDataService.getArticleByIdOrSlug(parameter);
            articlesManageDataService.deleteArticle(articleToDelete.getId());
        } catch (ArticleNotFoundException ex) {
            map.put(MESSAGE_ATTR, "Article that you requested to delete do not exist. Redirected here :)"); // todo: i18n
        }

        return "redirect:/articles";
    }
}
