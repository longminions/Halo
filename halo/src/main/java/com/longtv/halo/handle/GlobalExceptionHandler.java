package com.longtv.halo.handle;

import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
        ) {
            /**
             * Return the HTTP status code to use for the response.
             */
            @Override
            public HttpStatusCode getStatusCode() {
                return null;
            }

            /**
             * Return headers to use for the response.
             */
            @Override
            public HttpHeaders getHeaders() {
                return ErrorResponse.super.getHeaders();
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

            /**
             * Return a code to use to resolve the problem "type" for this exception
             * through a {@link MessageSource}. The type resolved through the
             * {@code MessageSource} will be passed into {@link URI#create(String)}
             * and therefore must be an encoded URI String.
             * <p>By default this is initialized via {@link #getDefaultTypeMessageCode(Class)}.
             *
             * @since 6.1
             */
            @Override
            public String getTypeMessageCode() {
                return ErrorResponse.super.getTypeMessageCode();
            }

            /**
             * Return a code to use to resolve the problem "title" for this exception
             * through a {@link MessageSource}.
             * <p>By default this is initialized via {@link #getDefaultTitleMessageCode(Class)}.
             */
            @Override
            public String getTitleMessageCode() {
                return ErrorResponse.super.getTitleMessageCode();
            }

            /**
             * Return a code to use to resolve the problem "detail" for this exception
             * through a {@link MessageSource}.
             * <p>By default this is initialized via
             * {@link #getDefaultDetailMessageCode(Class, String)}.
             */
            @Override
            public String getDetailMessageCode() {
                return ErrorResponse.super.getDetailMessageCode();
            }

            /**
             * Return arguments to use along with a {@link #getDetailMessageCode()
             * message code} to resolve the problem "detail" for this exception
             * through a {@link MessageSource}. The arguments are expanded
             * into placeholders of the message value, for example, "Invalid content type {0}".
             */
            @Override
            public Object[] getDetailMessageArguments() {
                return ErrorResponse.super.getDetailMessageArguments();
            }

            /**
             * Variant of {@link #getDetailMessageArguments()} that uses the given
             * {@link MessageSource} for resolving the message argument values.
             * <p>This is useful for example to expand message codes from validation errors.
             * <p>The default implementation delegates to {@link #getDetailMessageArguments()},
             * ignoring the supplied {@code MessageSource} and {@code Locale}.
             *
             * @param messageSource the {@code MessageSource} to use for the lookup
             * @param locale        the {@code Locale} to use for the lookup
             */
            @Override
            public Object[] getDetailMessageArguments(MessageSource messageSource, Locale locale) {
                return ErrorResponse.super.getDetailMessageArguments(messageSource, locale);
            }

            /**
             * Use the given {@link MessageSource} to resolve the
             * {@link #getTypeMessageCode() type}, {@link #getTitleMessageCode() title},
             * and {@link #getDetailMessageCode() detail} message codes, and then use the
             * resolved values to update the corresponding fields in {@link #getBody()}.
             *
             * @param messageSource the {@code MessageSource} to use for the lookup
             * @param locale        the {@code Locale} to use for the lookup
             */
            @Override
            public ProblemDetail updateAndGetBody(MessageSource messageSource, Locale locale) {
                return ErrorResponse.super.updateAndGetBody(messageSource, locale);
            }
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
