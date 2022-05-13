package ru.renett.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDto {
    private Long id;
    private String firstName;
    private String secondName;
    private String email;
    private String userName;
    private String password;
    private String passwordRepeat;
}
