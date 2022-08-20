package com.dkorb.familyapp.family.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FamilyNotExistsException extends RuntimeException {

    private static final String MESSAGE = "Family with id %d, does not exist!.";

    public FamilyNotExistsException(long familyId) {
        super(MESSAGE.formatted(familyId));
    }

}