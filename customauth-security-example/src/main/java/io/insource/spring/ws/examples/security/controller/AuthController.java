package io.insource.spring.ws.examples.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getIndex() {
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getLogin() {
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrf(CsrfToken csrfToken) {
        return csrfToken;
    }
}
