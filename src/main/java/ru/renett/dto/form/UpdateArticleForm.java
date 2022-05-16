package ru.renett.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleForm {
    private Long articleId;
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    private MultipartFile thumbnailImage;
    @NotBlank
    private List<String> tags;
}