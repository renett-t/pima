package ru.renett.controllers.article;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.renett.configuration.Constants;
import ru.renett.dto.ArticleDto;
import ru.renett.dto.UserDto;
import ru.renett.dto.response.LikeResponse;
import ru.renett.exceptions.ArticleNotFoundException;
import ru.renett.service.article.ArticlesGetDataService;
import ru.renett.service.article.ArticlesManageDataService;
import ru.renett.service.user.UsersService;

@RestController
@RequestMapping("/ajax")
@RequiredArgsConstructor
public class LikesController {

    private final ArticlesGetDataService articlesGetDataService;
    private final ArticlesManageDataService articlesManageDataService;
    private final UsersService usersService;

    @PostMapping("/articles/{article-id}/like")
    public ResponseEntity<LikeResponse> likeArticle(@PathVariable("article-id") String parameter,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        try {
            ArticleDto article = articlesGetDataService.getArticleByIdOrSlug(parameter);
            if (userDetails == null) {
                LikeResponse response = LikeResponse.builder()
                        .status(String.valueOf(HttpStatus.FORBIDDEN))
                        .message("Authorization Needed")
                        .source(null)
                        .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(response);
            } else {
                UserDto user = usersService.getUserByEmailOrUserName(userDetails.getUsername());
                boolean isLiked = articlesManageDataService.likeArticle(user.getId(), article.getId());
                String source = "";
                if (isLiked)
                    source = Constants.LIKE_SOURCE;
                else
                    source = Constants.DISLIKE_SOURCE;

                LikeResponse response = LikeResponse.builder()
                        .status(String.valueOf(HttpStatus.CREATED))
                        .message("Alright")
                        .source(source)
                        .isLiked(isLiked)
                        .build();
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(response);
            }
        } catch (ArticleNotFoundException ex) {
            LikeResponse response = LikeResponse.builder()
                    .status(String.valueOf(HttpStatus.NOT_FOUND))
                    .message("Requested article not found")
                    .source(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }
}
