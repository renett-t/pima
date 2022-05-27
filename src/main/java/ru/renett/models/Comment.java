package ru.renett.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "body", nullable = false, columnDefinition = "text")
    private String body;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
    private Article article;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "published_at", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP default current_timestamp")
    private Date publishedAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "parent_comment_id", nullable = true, referencedColumnName = "id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
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

        return "Comment{" + "id=" + id + ", body='" + body + '\'' + ", article=" + article.getId() + ", author=" + author.getId() + ", publishedAt=" + publishedAt + ", parentComment=" + parentNum + ", childComments=" + childComments + '}';
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
