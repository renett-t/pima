package ru.renett.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.renett.dto.UserDto;
import ru.renett.dto.form.SignUpForm;
import ru.renett.dto.form.SimpleUpdateUserForm;
import ru.renett.dto.form.UpdateUserRoleForm;
import ru.renett.dto.form.UpdateUserStateForm;
import ru.renett.exceptions.EntityNotFoundException;
import ru.renett.exceptions.PasswordsMismatchException;
import ru.renett.exceptions.ServiceException;
import ru.renett.exceptions.SignUpException;
import ru.renett.models.Role;
import ru.renett.models.User;
import ru.renett.repository.RolesRepository;
import ru.renett.repository.UsersRepository;
import ru.renett.service.user.UsersService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
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

        if (!checkPasswordsTheSame(dto.getPassword(), dto.getPasswordRepeat()))
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

    private boolean checkPasswordsTheSame(String password, String passwordRepeat) {
        return password != null && password.equals(passwordRepeat);
    }

    @Override
    public UserDto updateBasicUserData(SimpleUpdateUserForm form) throws EntityNotFoundException, PasswordsMismatchException {
        Optional<User> fromDb = usersRepository.findById(form.getId());
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

    private void save(User user) {
        usersRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        usersRepository.deleteById(userId);
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

    private boolean checkPasswords(String password, String encoded) {
        return passwordEncoder.encode(password).equals(encoded);
    }

}
