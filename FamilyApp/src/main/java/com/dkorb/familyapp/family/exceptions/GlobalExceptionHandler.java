package com.dkorb.familyapp.family.exceptions;

import com.dkorb.familyapp.family.dto.ErrorDetailsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDTO> handleGlobalException(Exception ex,
                                                                 WebRequest webRequest) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(new Date(), ex.getMessage(),
                webRequest.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FamilyIncorrectDataException.class)
    public ResponseEntity<ErrorDetailsDTO> handleFamilyIncorrectDataException(FamilyIncorrectDataException ex,
                                                                              WebRequest webRequest) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(new Date(), ex.getMessage(),
                webRequest.getDescription(false), HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FamilyNotExistsException.class)
    public ResponseEntity<ErrorDetailsDTO> handleFamilyNotExistsException(FamilyNotExistsException ex,
                                                                          WebRequest webRequest) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(new Date(), ex.getMessage(),
                webRequest.getDescription(false), HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberIncorrectAgeException.class)
    public ResponseEntity<ErrorDetailsDTO> handleMemberIncorrectAgeException(MemberIncorrectAgeException ex,
                                                                             WebRequest webRequest) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(new Date(), ex.getMessage(),
                webRequest.getDescription(false), HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}