package io.insource.spring.ws.examples.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @Value("${my.var:Default Value}")
    private String myVar;

    @GetMapping("/api/v1/config")
    public String getMyVar() {
        return myVar;
    }
}
