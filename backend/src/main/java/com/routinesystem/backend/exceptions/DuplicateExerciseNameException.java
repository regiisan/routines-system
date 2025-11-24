package com.routinesystem.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateExerciseNameException extends RuntimeException {
    public DuplicateExerciseNameException(String message) {
        super(message);
    }
}
