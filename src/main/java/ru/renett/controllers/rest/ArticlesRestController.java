package ru.renett.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.renett.configuration.Constants;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.rest.AddArticleDto;
import ru.renett.dto.rest.ArticlesPage;
import ru.renett.dto.rest.UpdateArticleDto;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.exceptions.InvalidArticlesRequestException;
import ru.renett.service.article.ArticlesRestService;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticlesRestController {
    private final ArticlesRestService articlesRestService;

    @GetMapping
    public ResponseEntity<ArticlesPage> getArticles(@RequestParam(name = "page", defaultValue = Constants.REST_DEFAULT_PAGE) int page, @RequestParam(name = "limit", defaultValue = Constants.REST_DEFAULT_LIMIT) int limit) {
        try {
            return ResponseEntity.ok(articlesRestService.getArticles(page, limit));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (InvalidArticlesRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getResult());
        }
    }

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
