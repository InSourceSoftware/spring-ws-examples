package io.insource.spring.ws.examples.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthorizationHeaderException extends AuthenticationException {
    public AuthorizationHeaderException(String msg) {
        super(msg);
    }

    public AuthorizationHeaderException(String msg, Throwable t) {
        super(msg, t);
    }
}
