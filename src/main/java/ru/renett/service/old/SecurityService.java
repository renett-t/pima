package ru.renett.service.old;

import ru.renett.exceptions.InvalidUserDataException;
import ru.renett.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityService {
    void signUp(User user, HttpServletRequest request, HttpServletResponse response) throws InvalidUserDataException;
    void signIn(String login, String password, HttpServletRequest request, HttpServletResponse response) throws InvalidUserDataException;
    boolean isAuthenticated(HttpServletRequest request);
    void logout(HttpServletRequest request, HttpServletResponse response);
    void editUserData(User user, HttpServletRequest request, HttpServletResponse response) throws InvalidUserDataException;
}
