package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.renett.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // todo: check if i could return here optional
    User findByUsername(String username);
    User findByEmail(String email);
}
