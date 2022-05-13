package ru.renett.service.user;

import ru.renett.dto.SignUpDto;
import ru.renett.dto.UpdateUserDto;
import ru.renett.dto.UpdateUserRoleDto;
import ru.renett.dto.UpdateUserStateDto;
import ru.renett.exceptions.ServiceException;
import ru.renett.exceptions.SignUpException;
import ru.renett.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void signUp(SignUpDto signUpDto) throws SignUpException;

    User updateUserData(UpdateUserDto dto);

    User updateUserRole(UpdateUserRoleDto dto);

    User updateUserState(UpdateUserStateDto dto);

    void save(User user);

    void delete(User user);

    User getUserByEmailOrUserName(String username) throws ServiceException;

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    Optional<User> findUserByEmail(String email);

    Optional<User> finsUserByUserName(String userName);


}
