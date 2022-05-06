package ru.renett.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.renett.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
