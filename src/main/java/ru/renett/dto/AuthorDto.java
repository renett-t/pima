package ru.renett.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.renett.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private Long id;
    private String firstName;
    private String secondName;
    private String userName;

    public static AuthorDto from(User user) {
        return AuthorDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .userName(user.getUserName())
                .build();
    }

    public static List<AuthorDto> from(List<User> authors) {
        return authors.stream().map(AuthorDto::from).collect(Collectors.toList());
    }
}
