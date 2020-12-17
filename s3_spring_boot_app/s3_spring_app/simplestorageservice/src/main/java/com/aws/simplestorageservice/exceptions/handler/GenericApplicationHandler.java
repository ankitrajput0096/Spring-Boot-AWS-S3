package com.aws.simplestorageservice.exceptions.handler;

import com.aws.simplestorageservice.exceptions.S3ApplicationException;
import com.aws.simplestorageservice.exceptions.S3ApplicationRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Objects;

import static com.aws.simplestorageservice.constants.ApplicationErrorConstants.INTERNAL_SERVER_ERROR;
import static com.aws.simplestorageservice.constants.ApplicationErrorConstants.INTERNAL_SERVER_ERROR_RUNTIME;

// Way to handle custom exceptions in spring boot application
@ControllerAdvice
public class GenericApplicationHandler extends ResponseEntityExceptionHandler {

    /**
     * Controller advice method to handle S3ApplicationException exceptions.
     *
     * @param ex         - {@link S3ApplicationException}
     * @param webRequest - {@link HttpServletRequest}
     * @return - {@link ResponseEntity}
     */
    @ExceptionHandler(S3ApplicationException.class)
    public final ResponseEntity<S3ApplicationErrorSchema> handleS3SpringBootApplicationException(
            final S3ApplicationException ex, final HttpServletRequest webRequest) {
        S3ApplicationErrorSchema e = new S3ApplicationErrorSchema(
                INTERNAL_SERVER_ERROR.getCode(),
                Objects.nonNull(ex.getMessage()) ? ex.getMessage() : INTERNAL_SERVER_ERROR.getMessage(),
                new Date().toString(),
                webRequest.getRequestURI());
        return new ResponseEntity<>(e, HttpStatus.valueOf(INTERNAL_SERVER_ERROR.getCode()));
    }

    /**
     * Controller advice method to handle S3ApplicationRuntimeException exceptions.
     *
     * @param ex         - {@link S3ApplicationRuntimeException}
     * @param webRequest - {@link HttpServletRequest}
     * @return - {@link ResponseEntity}
     */
    @ExceptionHandler(S3ApplicationRuntimeException.class)
    public final ResponseEntity<S3ApplicationErrorSchema> handleS3SpringBootApplicationRuntimeException(
            final S3ApplicationRuntimeException ex, final HttpServletRequest webRequest) {
        S3ApplicationErrorSchema e = new S3ApplicationErrorSchema(
                INTERNAL_SERVER_ERROR_RUNTIME.getCode(),
                Objects.nonNull(ex.getMessage()) ? ex.getMessage() : INTERNAL_SERVER_ERROR_RUNTIME.getMessage(),
                new Date().toString(),
                webRequest.getRequestURI());
        return new ResponseEntity<>(e, HttpStatus.valueOf(INTERNAL_SERVER_ERROR_RUNTIME.getCode()));
    }


}
