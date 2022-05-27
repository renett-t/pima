package ru.renett.controllers.article;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;
import ru.renett.configuration.Constants;
import ru.renett.dto.CommentDto;
import ru.renett.dto.UserDto;
import ru.renett.dto.form.CommentForm;
import ru.renett.dto.response.NewCommentResponse;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.service.article.CommentsService;
import ru.renett.service.user.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ajax")
public class CommentsController {
    private final UsersService usersService;
    private final CommentsService commentsService;
    private final Configuration configuration;

    @PostMapping("/articles/{article-id}/comment")
    @ResponseBody
    public ResponseEntity<NewCommentResponse> editArticle(@PathVariable("article-id") String parameter,
                                                          @AuthenticationPrincipal UserDetails userDetails,
                                                          CommentForm form,
                                                          HttpServletRequest request) {
        try {
            UserDto user = usersService.getUserByEmailOrUserName(userDetails.getUsername());
            CommentDto commentDto = commentsService.createComment(form, user.getId());

            String output = getHtmlOutput(request, "comment.ftlh", commentDto);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(NewCommentResponse.builder()
                            .status(String.valueOf(HttpStatus.CREATED))
                            .comment(commentDto)
                            .message("New comment successfully created")
                            .output(output)
                            .build()
                    );

        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest()
                    .body(NewCommentResponse.builder()
                            .status(String.valueOf(HttpStatus.BAD_REQUEST))
                            .comment(null)
                            .message("Bad request. Given params:" + form)
                            .output("")
                            .build());
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(NewCommentResponse.builder()
                            .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                            .comment(null)
                            .message("Cannot return html from template")
                            .output("")
                            .build());
        }
    }

    private String getHtmlOutput(HttpServletRequest request, String template, CommentDto data) throws IOException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put(Constants.COMMENT_ATTR, data);

        // https://stackoverflow.com/questions/11162830/expression-springmacrorequestcontext-is-undefined
        model.put(Constants.SPRING_MACRO_CONTEXT_ATTR, new RequestContext(request, null, null, null));
        try {
            configuration.getTemplate(template).process(model, stringWriter);
            return stringWriter.getBuffer().toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}
