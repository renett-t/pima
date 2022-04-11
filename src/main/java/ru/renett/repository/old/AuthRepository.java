package ru.renett.repository.old;

import ru.renett.models.old.AuthModel;
import ru.renett.models.User;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends CRUDRepository<AuthModel> {
    Optional<User> findUserByUUID(UUID uuid);
    Optional<AuthModel> findAuthModelByLogin(String login);
}
