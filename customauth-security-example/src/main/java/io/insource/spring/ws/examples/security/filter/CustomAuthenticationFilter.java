package io.insource.spring.ws.examples.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String BODY_ATTRIBUTE = CustomAuthenticationFilter.class.getSimpleName() + ".body";

    private final ObjectMapper objectMapper;

    public CustomAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Parse the request body as a HashMap and populate a request attribute
            UsernamePasswordRequest usernamePasswordRequest = objectMapper.readValue(request.getInputStream(), UsernamePasswordRequest.class);
            request.setAttribute(BODY_ATTRIBUTE, usernamePasswordRequest);
        } catch (IOException ex) {
            throw new AuthenticationCredentialsNotFoundException("Unable to parse JSON request body", ex);
        }

        return super.attemptAuthentication(request, response);
    }

    protected String obtainUsername(HttpServletRequest request) {
        UsernamePasswordRequest usernamePasswordRequest = (UsernamePasswordRequest) request.getAttribute(BODY_ATTRIBUTE);
        return usernamePasswordRequest.get(getUsernameParameter());
    }

    protected String obtainPassword(HttpServletRequest request) {
        UsernamePasswordRequest usernamePasswordRequest = (UsernamePasswordRequest) request.getAttribute(BODY_ATTRIBUTE);
        return usernamePasswordRequest.get(getPasswordParameter());
    }

    private static class UsernamePasswordRequest extends HashMap<String, String> {
        // Nothing, just a type marker
    }
}
