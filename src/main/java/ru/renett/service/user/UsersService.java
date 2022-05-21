package ru.renett.service.user;

import ru.renett.dto.UserDto;
import ru.renett.dto.form.*;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.exceptions.PasswordsMismatchException;
import ru.renett.exceptions.SignUpException;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    void signUp(SignUpForm signUpForm) throws SignUpException;

    UserDto updateBasicUserData(SimpleUpdateUserForm dto, Long userId) throws EntityNotFoundException, PasswordsMismatchException;

    UserDto updateUserRole(UpdateUserRoleForm dto);

    UserDto updateUserState(UpdateUserStateForm dto);

    void delete(Long userId);

    UserDto getUserByEmailOrUserName(String username) throws EntityNotFoundException;

    UserDto getUserById(Long id) throws EntityNotFoundException;

    List<UserDto> getAllUsers();

    UserDto findUserByEmail(String email) throws EntityNotFoundException;

    UserDto finsUserByUserName(String userName) throws EntityNotFoundException;
}
