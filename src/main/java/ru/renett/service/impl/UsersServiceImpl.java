package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.renett.dto.UserDto;
import ru.renett.dto.form.SignUpForm;
import ru.renett.dto.form.SimpleUpdateUserForm;
import ru.renett.dto.form.UpdateUserRoleForm;
import ru.renett.dto.form.UpdateUserStateForm;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.exceptions.PasswordsMismatchException;
import ru.renett.exceptions.SignUpException;
import ru.renett.models.Role;
import ru.renett.models.User;
import ru.renett.repository.RolesRepository;
import ru.renett.repository.UsersRepository;
import ru.renett.service.user.UsersService;
import ru.renett.utils.RolesCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesCache rolesCache;

    @Override
    public void signUp(SignUpForm dto) throws SignUpException {
        logger.info("New sign up request. Data = " + dto.getUserName() + ", " + dto.getEmail() + ".");
        Optional<User> emailUser = usersRepository.findUserByEmail(dto.getEmail());
        Optional<User> userNameUser = usersRepository.findUserByUserName(dto.getUserName());

        if (emailUser.isPresent())
            throw new SignUpException("User with email = " + dto.getEmail() + " already exists."); // todo: i18n message codes!!

        if (userNameUser.isPresent())
            throw new SignUpException("User with username = " + dto.getUserName() + " already exists.");

        if (!checkPasswordsTheSame(dto.getPassword(), dto.getPasswordRepeat()))
            throw new SignUpException("Passwords do not match.");

        User user = User.builder()
                .firstName(dto.getFirstName())
                .secondName(dto.getSecondName())
                .email(dto.getEmail())
                .userName(dto.getUserName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Set.of(rolesCache.getRoleByName(Role.ROLE.USER)))
                .state(User.State.NOT_CONFIRMED)
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        this.save(user);
    }

    private boolean checkPasswordsTheSame(String password, String passwordRepeat) {
        return password != null && password.equals(passwordRepeat);
    }

    @Override
    public UserDto updateBasicUserData(SimpleUpdateUserForm form, Long userId) throws EntityNotFoundException, PasswordsMismatchException {
        logger.debug("New update user request. Data includes: id=" + userId + ", fn=" + form.getFirstName() + ", sn=" + form.getSecondName() + ".");
        System.out.println("-------------------");
        System.out.println(form);
        Optional<User> fromDb = usersRepository.findById(userId);
        if (fromDb.isPresent()) {
            if (checkPasswords(form.getPassword(), fromDb.get().getPassword())) {
                throw new PasswordsMismatchException("Passwords do not match");
            } else {
                User user = fromDb.get();
                user.setFirstName(form.getFirstName());
                user.setSecondName(form.getSecondName());

                this.save(user);
                return UserDto.from(user);
            }
        } else {
            throw new EntityNotFoundException("User does not exist in DB.");
        }
    }

    @Override
    public UserDto updateUserRole(UpdateUserRoleForm dto) {
        throw new UnsupportedOperationException("emae");
        // todo update user role method
    }

    @Override
    public UserDto updateUserState(UpdateUserStateForm dto) {
        throw new UnsupportedOperationException("o mae ma wu");
        // todo update user state method
    }

    @Transactional
    void save(User user) {
        logger.info("Saving user in database. Data = " + user.toString() + ".");
        usersRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        logger.warn("Deleting user with id = " + userId + ".");
//        usersRepository.deleteById(userId);

        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + userId + " not found."));
        user.setState(User.State.DELETED);
        usersRepository.save(user);
    }

    @Override
    public UserDto getUserByEmailOrUserName(String username) throws EntityNotFoundException {
        Optional<User> userNameUser = usersRepository.findUserByUserName(username);

        if (userNameUser.isPresent())
            return UserDto.from(userNameUser.get());
        else {
            Optional<User> emailUser = usersRepository.findUserByEmail(username);

            if (emailUser.isPresent())
                return UserDto.from(emailUser.get());

            throw new EntityNotFoundException("No user for '" + username + "' found");
        }
    }

    @Override
    public UserDto getUserById(Long id) throws EntityNotFoundException {
        return UserDto.from(usersRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No user for id =" + id + " found")
        ));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return UserDto.from(usersRepository.findAll());
    }

    @Override
    public UserDto findUserByEmail(String email) throws EntityNotFoundException{
        return UserDto.from(usersRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("No user by email = " + email + " found")
        ));
    }

    @Override
    public UserDto finsUserByUserName(String userName) throws EntityNotFoundException{
        return UserDto.from(usersRepository.findUserByUserName(userName).orElseThrow(
                () -> new EntityNotFoundException("No user by username = " + userName + " found")
        ));
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.logout();
        } catch (ServletException ignored) {
            logger.error("Error while logging user out programmatically.");
        }
    }

    private boolean checkPasswords(String password, String encoded) {
        return passwordEncoder.encode(password).equals(encoded);
    }

}
