package ru.kpfu.itis.renett.repository;

import ru.kpfu.itis.renett.models.AuthModel;
import ru.kpfu.itis.renett.models.User;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends CRUDRepository<AuthModel> {
    Optional<User> findUserByUUID(UUID uuid);
    Optional<AuthModel> findAuthModelByLogin(String login);
}
