package ru.renett.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.renett.models.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String body;
    private Long parentId;
    private String publishedAt;
    private Long authorId;
    private String authorUserName;


    public static CommentDto from(Comment comment) {
        Comment parent = comment.getParentComment();
        Long parentId = null;
        if (parent != null) {
            parentId = parent.getId();
        }
        return CommentDto.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .parentId(parentId)
                .publishedAt(comment.getPublishedAt().toString())
                .authorId(comment.getAuthor().getId())
                .authorUserName(comment.getAuthor().getUserName())
                .build();
    }

    public static List<CommentDto> from(List<Comment> articles) {
        return articles.stream().map(CommentDto::from).collect(Collectors.toList());
    }
}
