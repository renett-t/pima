package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.renett.dto.form.SignUpForm;
import ru.renett.dto.form.UpdateUserForm;
import ru.renett.dto.form.UpdateUserRoleForm;
import ru.renett.dto.form.UpdateUserStateForm;
import ru.renett.exceptions.ServiceException;
import ru.renett.exceptions.SignUpException;
import ru.renett.models.Role;
import ru.renett.models.User;
import ru.renett.repository.RolesRepository;
import ru.renett.repository.UsersRepository;
import ru.renett.service.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;

    @Override
    public void signUp(SignUpForm dto) throws SignUpException {
        // validation (done by Spring)
        Optional<User> emailUser = usersRepository.findUserByEmail(dto.getEmail());
        Optional<User> userNameUser = usersRepository.findUserByUserName(dto.getUserName());

        if (emailUser.isPresent())
            throw new SignUpException("User with email = " + dto.getEmail() + " already exists."); // todo: i18n message codes!!

        if (userNameUser.isPresent())
            throw new SignUpException("User with username = " + dto.getUserName() + " already exists.");

        if (!checkPasswords(dto.getPassword(), dto.getPasswordRepeat()))
            throw new SignUpException("Passwords do not match.");

        System.out.println("------------------------ REGISTRATION ------------------------");
        System.out.println(dto);
        User user = User.builder()
                .firstName(dto.getFirstName())
                .secondName(dto.getSecondName())
                .email(dto.getEmail())
                .userName(dto.getUserName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Set.of(rolesRepository.getRoleByName(Role.ROLE.USER)))
                .state(User.State.NOT_CONFIRMED)
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        System.out.println("PASSWORD=" + dto.getPassword() + ";ENCODED=" + user.getPassword());
        this.save(user);
    }

    @Override
    public User updateUserData(UpdateUserForm dto) throws ServiceException {
        if (checkPasswords(dto.getPassword(), dto.getPasswordRepeat())) {
            throw new ServiceException("Passwords do not match");
        } else {
            Optional<User> fromDb = usersRepository.findById(dto.getId());
            if (fromDb.isPresent()) {
                // todo: update User Data without touching roles & state - custom method in repository
                User user = User.builder()
                        .id(dto.getId())
                        .firstName(dto.getFirstName())
                        .secondName(dto.getSecondName())
                        .email(dto.getEmail())
                        .userName(dto.getUserName())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .roles(fromDb.get().getRoles())
                        .state(fromDb.get().getState())
                        .build();
                this.save(user);
                return user;
            } else {
                throw new ServiceException("User does not exist in DB.");
            }
        }
    }

    @Override
    public User updateUserRole(UpdateUserRoleForm dto) throws ServiceException {
        throw new UnsupportedOperationException("emae");
        // todo update user role method
    }

    @Override
    public User updateUserState(UpdateUserStateForm dto) throws ServiceException {
        throw new UnsupportedOperationException("o mae ma wu");
        // todo update user state method
    }

    @Override
    public void save(User user) throws ServiceException {
        usersRepository.save(user);
    }

    @Override
    public void delete(User user) throws ServiceException {
        usersRepository.delete(user);
    }

    @Override
    public User getUserByEmailOrUserName(String username) {
        Optional<User> userNameUser = usersRepository.findUserByUserName(username);

        if (userNameUser.isPresent())
            return userNameUser.get();
        else {
            Optional<User> emailUser = usersRepository.findUserByEmail(username);

            if (emailUser.isPresent())
                return emailUser.get();

            throw new ServiceException("No user for '" + username + "' found");
        }
    }

    @Override
    public Optional<User> getUserById(Long id) throws ServiceException {
        return usersRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        return usersRepository.findAll();
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws ServiceException {
        return usersRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> finsUserByUserName(String userName) throws ServiceException {
        return usersRepository.findUserByUserName(userName);
    }

    private boolean checkPasswords(String password, String passwordRepeat) {
        return password.equals(passwordRepeat);
    }

}
