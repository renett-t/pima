package ru.renett.models;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 3, max = 255)
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "body", nullable = false, columnDefinition = "text")
    private String body;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

//    @CreationTimestamp
    @Column(name = "published_at", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedAt;

    @Column(name = "thumbnail_path", nullable = false)
    private String thumbnailPath;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags;

    @Column(name = "view_count", nullable = false)
    private Long viewAmount;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Like> likes;

    @Transient
    private int commentAmount;

    @Transient
    private int likeAmount;

    public int getLikeAmount() {
        return likes.size();
    }

    public int getCommentAmount() {
        return commentList.size();
    }

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
