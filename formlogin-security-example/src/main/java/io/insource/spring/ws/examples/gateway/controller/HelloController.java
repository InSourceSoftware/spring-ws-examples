package io.insource.spring.ws.examples.gateway.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {
    @GetMapping("/hello")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String sayHello() {
        return "Hello, World";
    }
}
