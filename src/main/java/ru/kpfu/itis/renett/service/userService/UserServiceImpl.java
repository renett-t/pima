package ru.kpfu.itis.renett.service.userService;

import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

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
