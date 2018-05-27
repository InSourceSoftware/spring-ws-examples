package io.insource.spring.ws.examples.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    @GetMapping(value = {"/", "/auth/status"})
    public StatusResponse getStatus() {
        String username = Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .map(Object::toString)
            .orElse("anonymous");

        return "anonymous".equals(username)
            ? new StatusResponse("Failed", "Please provide your credentials to use this site.")
            : new StatusResponse("Success", "You are fully authenticated.");
    }

    public static class StatusResponse {
        private final String status;
        private final String statusText;

        StatusResponse(String status, String statusText) {
            this.status = status;
            this.statusText = statusText;
        }

        public String getStatus() {
            return status;
        }

        public String getStatusText() {
            return statusText;
        }
    }
}
