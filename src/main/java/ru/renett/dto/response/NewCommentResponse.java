package ru.renett.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.renett.dto.CommentDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentResponse {
    private String status;
    private String message;
    private CommentDto comment;
    private String output;
}
