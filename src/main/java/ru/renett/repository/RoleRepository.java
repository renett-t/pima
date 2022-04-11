package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.renett.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
