package ru.renett.models.old;

import lombok.*;
import ru.renett.models.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {
    @Id
    private Long id;

    private String title;
    private String body;

    private User author;
    private Date publishedAt;
    private String thumbnailPath;

    private List<Comment> commentList;
    private List<Tag> tagList;

    private Long viewAmount;
    private Long commentAmount;
    private Long likeAmount;

    public Article(Long id) {
        this.id = id;
    }

    public Article(String title, String body, User author, String thumbnailPath) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.thumbnailPath = thumbnailPath;
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
