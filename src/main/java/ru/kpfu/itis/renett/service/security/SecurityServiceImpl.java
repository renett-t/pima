package ru.kpfu.itis.renett.service.security;

import ru.kpfu.itis.renett.exceptions.InvalidUserDataException;
import ru.kpfu.itis.renett.models.AuthModel;
import ru.kpfu.itis.renett.models.User;
import ru.kpfu.itis.renett.repository.AuthRepository;
import ru.kpfu.itis.renett.repository.UserRepository;
import ru.kpfu.itis.renett.service.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

public class SecurityServiceImpl implements SecurityService {
    private final UserRepository userRepository;
    private final UserDataValidator userDataValidator;
    private final AuthRepository authRepository;
    private final EncoderInterface encoder;

    public SecurityServiceImpl(UserRepository userRepository, AuthRepository authRepository, EncoderInterface encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userDataValidator = new UserDataValidator();
        this.authRepository = authRepository;
    }

    @Override
    public void signUp(User user, HttpServletRequest request, HttpServletResponse response) {
        UUID uuid = null;
        HttpSession session = request.getSession(true);
        String rawPass = request.getParameter("password");
        String repeatedPass = request.getParameter("repeatedPassword");

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new InvalidUserDataException("Пользователь с почтой " + user.getEmail() + "уже был зарегистрирован.");
        }

        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new InvalidUserDataException("Пользователь с логином " + user.getLogin() + "уже был зарегистрирован.");
        }

        if (userDataValidator.isUserParametersCorrect(user.getFirstName(), user.getSecondName(), user.getEmail(), user.getLogin(), rawPass, repeatedPass)) {
            user.setPasswordHash(encoder.encodeString(rawPass));

            uuid = UUID.randomUUID();
            AuthModel authModel = AuthModel.builder().login(user.getLogin()).uuid(uuid).build();

            userRepository.save(user);
            authRepository.save(authModel);

            session.setAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME, user);
            setAuthorizedCookieToResponse(uuid, response);
        }
    }

    @Override
    public void signIn(String login, String password, HttpServletRequest request, HttpServletResponse response) {
        UUID uuid = null;
        HttpSession session = request.getSession(true);

        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (!optionalUser.isPresent()) {
            throw new InvalidUserDataException("Пользователь с логином " + login + " не был найден. Повторите попытку.");
        }

        String passHash;
        String passToCompare;

        if (password == null) {
            passHash = "null";
        } else {
            passHash = encoder.encodeString(password);
        }
        passToCompare = String.valueOf(optionalUser.get().getPasswordHash());

        if (passHash.equals(passToCompare)) {
            uuid = UUID.randomUUID();
            Optional<AuthModel> authModelFromRepo = authRepository.findAuthModelByLogin(login);
            if (authModelFromRepo.isPresent()) {
                authRepository.update(AuthModel.builder().login(login).uuid(uuid).build());
            } else {
                authRepository.save(AuthModel.builder().login(login).uuid(uuid).build());
            }
            session.setAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME, optionalUser.get());
            setAuthorizedCookieToResponse(uuid, response);
        } else {
            throw new InvalidUserDataException("Неверный пароль.");
        }
    }

    @Override
    public boolean isAuthenticated(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        User userFromSession;
        if (httpSession == null) {
            return false;
        }
        userFromSession = (User) httpSession.getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        return userFromSession != null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession(false);
        User userToLogOut = (User) httpSession.getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);

        if (userToLogOut != null) {
            removeAuthorizedCookieFromRequest(userToLogOut.getLogin(), request);
        }

        httpSession.removeAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
    }

    @Override
    public void editUserData(User user, HttpServletRequest request, HttpServletResponse response) {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("password");

        User userFromSession = (User) request.getSession().getAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME);
        user.setId(userFromSession.getId());

        if (userFromSession.getPasswordHash() == null) {  // password wasn't set -> auth from vk
            if (newPassword != null && !newPassword.equals("")) {   // user specified new pass
                if (userDataValidator.isPasswordCorrect(newPassword)) {
                    user.setPasswordHash(encoder.encodeString(newPassword));
                }
            }
        } else {  // ordinary user
            if (!userFromSession.getPasswordHash().equals(encoder.encodeString(oldPassword))) {
                throw new InvalidUserDataException("Пароль не совпадает с прежним.");
            }

            if (newPassword != null && !newPassword.equals("")) {
                userDataValidator.isPasswordCorrect(newPassword);
                user.setPasswordHash(encoder.encodeString(newPassword));
            } else {   // new pass wasn't specified
                user.setPasswordHash(userFromSession.getPasswordHash());
            }
        }

        if (userDataValidator.isUserParametersCorrect(user.getFirstName(), user.getSecondName(), user.getEmail(), user.getLogin())) {
            UUID newUuid = UUID.randomUUID();
            user.setId(userFromSession.getId());
            AuthModel authModel = AuthModel.builder().login(user.getLogin()).uuid(newUuid).build();

            userRepository.update(user);
            authRepository.update(authModel);

            request.getSession().setAttribute(Constants.SESSION_USER_ATTRIBUTE_NAME, user);
            setAuthorizedCookieToResponse(newUuid, response);
        }
    }

    private void setAuthorizedCookieToResponse(UUID uuid, HttpServletResponse response) {
        Cookie authorizedCookie = new Cookie(Constants.COOKIE_AUTHORIZED_NAME, uuid.toString());
        authorizedCookie.setMaxAge(60*60*24);
        response.addCookie(authorizedCookie);
    }

    private void removeAuthorizedCookieFromRequest(String login, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.COOKIE_AUTHORIZED_NAME)) {
                    AuthModel authModel = AuthModel.builder().login(login).build();
                    try {
                        authModel.setUuid(UUID.fromString(cookie.getValue()));
                    } catch (IllegalArgumentException ignored) {   // если в куках неправильные данные, без разницы, её всё равно нужно удалить
                    }
                    authRepository.delete(authModel);
                    cookie.setMaxAge(-1);
                }
            }
        }
    }
}
