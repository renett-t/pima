package ru.kpfu.itis.renett.repository;

import ru.kpfu.itis.renett.models.User;

import java.util.Optional;

public interface UserRepository extends CRUDRepository<User> {
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
}
