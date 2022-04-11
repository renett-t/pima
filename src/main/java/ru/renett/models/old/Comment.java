package ru.renett.models.old;

import lombok.*;
import ru.renett.models.User;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Comment {
    private Long id;
    private String body;
    private Article article;
    private User author;
    private Date publishedAt;
    private Comment parentComment;
    private List<Comment> childComments;

    public Comment(String body, Article article, User author, Comment parentComment) {
        this.body = body;
        this.article = article;
        this.author = author;
        this.parentComment = parentComment;
    }

    public Comment(Long id) {
        this.id = id;
    }

    public Comment(Long i, String s) {
        id = i;
        body = s;
    }

    @Override
    public String toString() {
        Long parentNum = -1L;
        if (parentComment != null) {
            parentNum = parentComment.id;
        }

        return "Comment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", article=" + article.getId() +
                ", author=" + author.getId() +
                ", publishedAt=" + publishedAt +
                ", parentComment=" + parentNum +
                ", childComments=" + childComments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) && body.equals(comment.body) && (article.getId() == comment.getAuthor().getId()) && (author.getId() == (comment.getAuthor().getId()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, article.getId(), author.getId());
    }
}
