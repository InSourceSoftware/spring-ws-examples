package io.insource.spring.ws.examples.stateless.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class HelloController {
    @GetMapping("/hello")
    public String sayHello(HttpServletRequest request) {
        String username = request.getHeader("X-USERNAME");

        return String.format("Hello, %s!", username);
    }

    @GetMapping("/anonymous")
    public String sayNothing() {
        return "I have nothing to say to you. Who are you?";
    }
}
