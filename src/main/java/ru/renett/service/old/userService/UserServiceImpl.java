package ru.renett.service.old.userService;

import ru.renett.models.User;
import ru.renett.repository.UsersRepository;

public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public void editUserData(User user) {
        usersRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        usersRepository.delete(user);
    }

    @Override
    public void addNewUser(User user) {
        usersRepository.save(user);
    }
}
