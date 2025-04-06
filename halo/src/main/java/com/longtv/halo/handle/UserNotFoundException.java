package com.longtv.halo.handle;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException() {
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse() {
            /**
             * Return the HTTP status code to use for the response.
             */
            @Override
            public HttpStatusCode getStatusCode() {
                return null;
            }

            /**
             * Return the body for the response, formatted as an RFC 9457
             * {@link ProblemDetail} whose {@link ProblemDetail#getStatus() status}
             * should match the response status.
             * <p><strong>Note:</strong> The returned {@code ProblemDetail} may be
             * updated before the response is rendered, for example, via
             * {@link #updateAndGetBody(MessageSource, Locale)}. Therefore, implementing
             * methods should use an instance field, and should not re-create the
             * {@code ProblemDetail} on every call, nor use a static variable.
             */
            @Override
            public ProblemDetail getBody() {
                return null;
            }
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
