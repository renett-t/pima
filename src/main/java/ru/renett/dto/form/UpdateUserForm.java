package ru.renett.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// WARNING. This form is not used, actually.
public class UpdateUserForm {
    private Long id;

    @NotBlank
    @Email(message = "{valid.email}")
    private String email;

    @NotBlank
    @Length(min = 5, max = 32, message = "{valid.userName, {min}, {max}}") //todo :(
    private String userName;

    @NotBlank
    @Length(min = 5, message = "{valid.password}")
    private String password;

    @Length(min = 5, message = "{valid.password}")
    private String newPassword;
}
