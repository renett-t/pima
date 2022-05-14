package ru.renett.dto.form;

import lombok.Builder;
import lombok.Data;
import ru.renett.models.Role;

import java.util.Set;

@Data
@Builder
public class UpdateUserRoleForm {
    private Long id;
    private Set<Role> newRoles;
}
