package ru.renett.service.old;

import ru.renett.exceptions.InvalidUserDataException;

public interface UserDataValidatorInterface {
    boolean isNameCorrect(String name) throws InvalidUserDataException;
    boolean isEmailCorrect(String email) throws InvalidUserDataException;
    boolean isLoginCorrect(String login) throws InvalidUserDataException;
    boolean arePasswordsCorrect(String password, String repeatedPassword) throws InvalidUserDataException;
    boolean isPasswordCorrect(String password) throws InvalidUserDataException;
    boolean isUserParametersCorrect(String firstName, String secondName,
                                    String email, String login, String password, String repeatedPassword) throws InvalidUserDataException;

    boolean isUserParametersCorrect(String firstName, String secondName, String email, String login) throws InvalidUserDataException;
}
