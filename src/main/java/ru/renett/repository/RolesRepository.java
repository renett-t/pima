package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.renett.models.Role;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(Role.ROLE name);
}
