package ru.renett.controllers.article;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.renett.dto.CommentDto;
import ru.renett.dto.UserDto;
import ru.renett.dto.form.CommentForm;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.models.User;
import ru.renett.service.article.CommentsService;
import ru.renett.service.user.UsersService;

@Controller
@RequiredArgsConstructor
public class CommentsController {
    private final UsersService usersService;
    private final CommentsService commentsService;

    @PostMapping("/articles/{article-id}/comment")
    @ResponseBody
    public ResponseEntity<?> editArticle(@PathVariable("article-id") String parameter,
                                         @AuthenticationPrincipal UserDetails userDetails,
                                         CommentForm form,
                                         ModelMap map) {
        try {
            UserDto user = usersService.getUserByEmailOrUserName(userDetails.getUsername());
            CommentDto commentDto = commentsService.createComment(form, user.getId());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(commentDto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
