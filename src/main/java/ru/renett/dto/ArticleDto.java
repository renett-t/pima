package ru.renett.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.renett.models.Article;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String body;
    private String thumbnail;
    private String publishedAt;
    private int views;
    private int likes;
    private int commentsAmount;

    private AuthorDto author;
    private List<CommentDto> comments;
    private Set<TagDto> tags;

    public static ArticleDto from(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .thumbnail(article.getThumbnailPath())
                .publishedAt(article.getPublishedAt().toString())
                .views(Math.toIntExact(article.getViewAmount()))
                .likes(article.getLikeAmount())
                .author(AuthorDto.from(article.getAuthor()))
                .commentsAmount(article.getCommentAmount())
                .comments(CommentDto.from(article.getCommentList()))
                .tags(TagDto.from(article.getTags()))
                .build();
    }

    public static List<ArticleDto> from(List<Article> articles) {
        return articles.stream().map(ArticleDto::from).collect(Collectors.toList());
    }
}
