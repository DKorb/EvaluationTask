package com.dkorb.familyapp.family.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MemberIncorrectAgeException extends RuntimeException {

    private static final String MESSAGE = "age %d of family member is incorrectly!.";

    public MemberIncorrectAgeException(int age) {
        super(MESSAGE.formatted(age));
    }

}