package ru.kpfu.itis.renett.service.security;

import ru.kpfu.itis.renett.exceptions.InvalidRequestDataException;

public class RequestValidator implements RequestValidatorInterface{
    @Override
    public int checkRequestedIdCorrect(String id) throws InvalidRequestDataException {
        int parsedValue = 0;
        if (id == null) {
            throw new InvalidRequestDataException("id is null");
        }
        try {
            parsedValue = Integer.parseInt(id);
            if (parsedValue < 1) {
                throw new InvalidRequestDataException("id value cannot be non positive");
            }
        } catch (NumberFormatException e) {
            throw new InvalidRequestDataException("Cannot parse to int value", e);
        }

        return parsedValue;
    }
}
