package com.dkorb.familyapp.family.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FamilyIncorrectDataException extends RuntimeException {

    private static final String MESSAGE = "Incorrect family data was entered!.";

    public FamilyIncorrectDataException() {
        super(MESSAGE);
    }

}