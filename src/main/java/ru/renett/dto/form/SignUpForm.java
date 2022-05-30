package ru.renett.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.renett.validation.SameFields;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SameFields(one = "password", two = "passwordRepeat", message = "{valid.passwords}")
public class SignUpForm {
    private String firstName;
    private String secondName;
    @NotBlank
    @Email(message = "{valid.email}")
    private String email;

    @NotBlank
    @Length(min = 5, max = 32, message = "{valid.userName}")
    private String userName;

    @NotBlank
    @Length(min = 5, message = "{valid.password}")
    private String password;

    @NotBlank
    @Length(min = 5, message = "{valid.password}")
    private String passwordRepeat;
}
