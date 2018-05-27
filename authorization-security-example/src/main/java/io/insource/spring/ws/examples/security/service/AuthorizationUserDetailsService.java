package io.insource.spring.ws.examples.security.service;

import io.insource.spring.ws.examples.security.config.WebSecurityConfiguration;
import io.insource.spring.ws.examples.security.exception.AuthorizationHeaderException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.regex.Matcher;

/* Not marked @Component to simplify WebSecurityConfiguration. */
public class AuthorizationUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationUserDetailsService.class);

    @Override
    @Cacheable(value = "users", key = "#authorizationHeader")
    public UserDetails loadUserByUsername(String authorizationHeader) throws UsernameNotFoundException {
        logger.info("Loading user for Authorization header: " + authorizationHeader);

        // Check authentication scheme
        if (!authorizationHeader.startsWith(WebSecurityConfiguration.REALM_NAME)) {
            throw new AuthorizationHeaderException("Invalid authentication scheme found in Authorization header");
        }

        // Check for apikey parameter
        if (!authorizationHeader.contains(WebSecurityConfiguration.API_KEY_PARAM)) {
            throw new AuthorizationHeaderException("Invalid authentication scheme found in Authorization header");
        }

        // Check that the Authorization header matches the pattern
        Matcher matcher = WebSecurityConfiguration.AUTHORIZATION_HEADER_PATTERN.matcher(authorizationHeader);
        if (!matcher.matches()) {
            throw new AuthorizationHeaderException("Unable to parse apikey from Authorization header");
        }

        return loadUserDetails(matcher.group());
    }

    private UserDetails loadUserDetails(String apiKey) {
        // TODO: Implement with your own logic to resolve API key (from Authentication header value)
        return new User("user", apiKey, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
