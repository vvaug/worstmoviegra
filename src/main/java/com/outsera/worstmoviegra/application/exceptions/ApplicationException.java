package com.outsera.worstmoviegra.application.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    protected final HttpStatus httpStatus;
    protected final Class<? extends Throwable> rootCause;

    public ApplicationException(String message, HttpStatus httpStatus, Class<? extends Throwable> rootCause) {
        super(message);
        this.httpStatus = httpStatus;
        this.rootCause = rootCause;
    }
}
