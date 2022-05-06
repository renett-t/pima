package ru.renett.service.old.userService;

import ru.renett.models.User;

public interface UserService {
    User getUserById(Long id);
    void editUserData(User user);
    void deleteUser(User user);
    void addNewUser(User user);
}
