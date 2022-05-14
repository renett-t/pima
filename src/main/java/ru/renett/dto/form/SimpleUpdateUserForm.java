package ru.renett.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUpdateUserForm {
    private Long id;
    @NotBlank
    @Length(min = 5, message = "{valid.name}")
    private String firstName;

    @NotBlank
    @Length(min = 5, message = "{valid.name}")
    private String secondName;

    @NotBlank
    @Length(min = 5, message = "{valid.password}")
    private String password;
}
