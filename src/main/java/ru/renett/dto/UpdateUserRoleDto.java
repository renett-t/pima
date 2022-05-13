package ru.renett.dto;

import lombok.Builder;
import lombok.Data;
import ru.renett.models.Role;

import java.util.Set;

@Data
@Builder
public class UpdateUserRoleDto {
    private Long id;
    private Set<Role> newRoles;
}
