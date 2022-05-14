package ru.renett.service.user;

import ru.renett.dto.form.SignUpForm;
import ru.renett.dto.form.UpdateUserForm;
import ru.renett.dto.form.UpdateUserRoleForm;
import ru.renett.dto.form.UpdateUserStateForm;
import ru.renett.exceptions.ServiceException;
import ru.renett.exceptions.SignUpException;
import ru.renett.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void signUp(SignUpForm signUpForm) throws SignUpException;

    User updateUserData(UpdateUserForm dto);

    User updateUserRole(UpdateUserRoleForm dto);

    User updateUserState(UpdateUserStateForm dto);

    void save(User user);

    void delete(User user);

    User getUserByEmailOrUserName(String username) throws ServiceException;

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    Optional<User> findUserByEmail(String email);

    Optional<User> finsUserByUserName(String userName);


}
