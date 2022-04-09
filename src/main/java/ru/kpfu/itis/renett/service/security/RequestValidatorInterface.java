package ru.kpfu.itis.renett.service.security;

import ru.kpfu.itis.renett.exceptions.InvalidRequestDataException;

public interface RequestValidatorInterface {
    /**
     * Checks if given ID value is not null, can be parsed to Integer, is positive and returns that int value. Otherwise, it throws InvalidRequestDataException
     * @param id id string value from request
     * @return parsed int value
     * @throws InvalidRequestDataException
     */
    int checkRequestedIdCorrect(String id) throws InvalidRequestDataException;
}
