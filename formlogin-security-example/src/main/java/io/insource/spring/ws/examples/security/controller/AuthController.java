package io.insource.spring.ws.examples.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class AuthController {
    private final CsrfTokenRepository csrfTokenRepository;

    @Inject
    public AuthController(CsrfTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @GetMapping(value = {"/", "/auth/status"})
    public StatusResponse getStatus() {
        String username = Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .map(Object::toString)
            .orElse("anonymous");

        String statusText = "anonymous".equals(username)
            ? "You have been securely logged out."
            : "You have successfully logged in.";

        return StatusResponse.createSuccess(statusText);
    }

    @GetMapping("/auth/session")
    public SessionResponse getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession httpSession = requestAttributes.getRequest().getSession(false);
        String sessionId = httpSession.getId();
        String csrfToken = csrfTokenRepository.loadToken(requestAttributes.getRequest()).getToken();

        return new SessionResponse(sessionId, csrfToken);
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

        static StatusResponse createSuccess(String statusText) {
            return new StatusResponse("Success", statusText);
        }
    }

    public static class SessionResponse {
        private final String sessionToken;
        private final String csrfToken;

        SessionResponse(String sessionToken, String csrfToken) {
            this.sessionToken = sessionToken;
            this.csrfToken = csrfToken;
        }

        public String getSessionToken() {
            return sessionToken;
        }

        public String getCsrfToken() {
            return csrfToken;
        }
    }
}
