package ru.renett.dto;

import lombok.Builder;
import lombok.Data;
import ru.renett.models.User;

@Data
@Builder
public class UpdateUserStateDto {
    private Long id;
    private User.State newState;
}
