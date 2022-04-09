package ru.kpfu.itis.renett.service.userService;

import ru.kpfu.itis.renett.models.User;

public interface UserService {
    User getUserById(int id);
    void editUserData(User user);
    void deleteUser(User user);
    void addNewUser(User user);
}
