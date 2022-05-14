package ru.renett.dto.form;

import lombok.Builder;
import lombok.Data;
import ru.renett.models.User;

@Data
@Builder
public class UpdateUserStateForm {
    private Long id;
    private User.State newState;
}
