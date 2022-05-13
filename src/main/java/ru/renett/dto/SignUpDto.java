package ru.renett.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String firstName;
    private String secondName;
    private String email;
    private String userName;
    private String password;
    private String passwordRepeat;
}
