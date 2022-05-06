package ru.renett.service.old;

import ru.renett.exceptions.InvalidRequestDataException;

public class RequestValidator implements RequestValidatorInterface{
    @Override
    public Long checkRequestedIdCorrect(String id) throws InvalidRequestDataException {
        long parsedValue = 0L;
        if (id == null) {
            throw new InvalidRequestDataException("id is null");
        }
        try {
            parsedValue = Long.parseLong(id);
            if (parsedValue < 1) {
                throw new InvalidRequestDataException("id value cannot be non positive");
            }
        } catch (NumberFormatException e) {
            throw new InvalidRequestDataException("Cannot parse to int value", e);
        }

        return parsedValue;
    }
}
