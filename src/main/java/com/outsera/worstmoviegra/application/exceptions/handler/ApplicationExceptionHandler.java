package com.outsera.worstmoviegra.application.exceptions.handler;

import com.outsera.worstmoviegra.application.exceptions.ApplicationException;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException applicationException){
        return ResponseEntity.status(applicationException.getHttpStatus())
                .body(ErrorResponse.builder()
                        .errorMessage(applicationException.getMessage())
                        .httpStatus(applicationException.getHttpStatus().value())
                        .cause(applicationException.getRootCause().getName())
                        .build());
    }

    @Builder
    @Data
    public static class ErrorResponse {
        private Integer httpStatus;
        private String errorMessage;
        private String cause;
    }
}
