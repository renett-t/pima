package ru.kpfu.itis.renett.models;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Article {
    private Integer id;
    private String title;
    private String body;
    private User author;
    private Date publishedAt;
    private String thumbnailPath;
    private List<Comment> commentList;
    private List<Tag> tagList;
    private Long viewAmount;
    private int commentAmount;
    private int likeAmount;

    public Article(Integer id) {
        this.id = id;
    }

    public Article(String title, String body, User author, String thumbnailPath) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.thumbnailPath = thumbnailPath;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", author=" + author.getId() +
                ", publishedAt=" + publishedAt +
                ", thumbnailPath='" + thumbnailPath + '\'' +
                ", commentList=" + commentList +
                ", tagList=" + tagList +
                ", viewCount=" + viewAmount +
                ", commentAmount=" + commentAmount +
                ", likeAmount=" + likeAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id) && Objects.equals(title, article.title) && Objects.equals(body, article.body) && (author.getId() == (article.author.getId()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, author.getId());
    }
}
