package ru.renett.service.old.userService;

import ru.renett.models.User;
import ru.renett.repository.UserRepository;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void editUserData(User user) {
        userRepository.update(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public void addNewUser(User user) {
        userRepository.save(user);
    }
}
