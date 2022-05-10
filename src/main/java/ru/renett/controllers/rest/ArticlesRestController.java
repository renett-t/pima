package ru.renett.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.renett.dto.AddArticleDto;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.ArticlesPage;
import ru.renett.dto.UpdateArticleDto;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.service.article.ArticlesRestService;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticlesRestController {
    private final ArticlesRestService articlesRestService;

    @Value("${defaults.rest.page}")
    private static final String defaultPage = "";

    @Value("${defaults.rest.limit}")
    private static final String defaultLimit = "";

    @GetMapping
    public ResponseEntity<ArticlesPage> getArticles(@RequestParam(name = "page", defaultValue = defaultPage) int page, @RequestParam(name = "limit", defaultValue = defaultLimit) int limit) {
        try {
            return ResponseEntity.ok(articlesRestService.getArticles(page, limit));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping
//    public ResponseEntity<ArticlesPage> getArticlesDefaults() {
//        try {
//            return ResponseEntity.ok(articlesRestService.getArticles(defaultPage, defaultLimit));
//        } catch (EntityNotFoundException ex) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PostMapping
    public ResponseEntity<?> addNewArticle(@RequestBody AddArticleDto addArticleDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(articlesRestService.addNewArticle(addArticleDto));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{article-id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable("article-id") Long id) {
        try {
            return ResponseEntity.ok(articlesRestService.getArticleById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{article-id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable("article-id") Long id, @RequestBody UpdateArticleDto updateArticleDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(articlesRestService.updateArticle(id, updateArticleDto));
    }

    @DeleteMapping("/{article-id}")
    public ResponseEntity<?> updateArticle(@PathVariable("article-id") Long id) {
        articlesRestService.deleteArticleById(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }
}
