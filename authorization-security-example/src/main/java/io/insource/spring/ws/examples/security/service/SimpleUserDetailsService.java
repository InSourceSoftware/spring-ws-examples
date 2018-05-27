package io.insource.spring.ws.examples.security.service;

import io.insource.spring.ws.examples.security.config.WebSecurityConfiguration;
import io.insource.spring.ws.examples.security.exception.AuthorizationHeaderException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Not marked @Component to simplify WebSecurityConfiguration. */
public class SimpleUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(SimpleUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String authorizationHeader) throws UsernameNotFoundException {
        logger.info("Loading user for Authorization header: " + authorizationHeader);

        // Check authentication scheme
        if (!authorizationHeader.startsWith(WebSecurityConfiguration.REALM_NAME)) {
            throw new AuthorizationHeaderException("Invalid authentication scheme found in Authorization header");
        }

        if (!authorizationHeader.contains(WebSecurityConfiguration.API_KEY_PARAM)) {
            throw new AuthorizationHeaderException("Invalid authentication scheme found in Authorization header");
        }

        Matcher matcher = WebSecurityConfiguration.AUTHORIZATION_HEADER_PATTERN.matcher(authorizationHeader);
        if (!matcher.matches()) {
            throw new AuthorizationHeaderException("Unable to parse apikey from Authorization header");
        }

        return loadUserDetails(matcher.group());
    }

    private UserDetails loadUserDetails(String apiKey) {
        // TODO: Implement with your own logic to resolve API key (from Authentication header value)

        // Note: In order for the user caching to work the Spring way, the API key
        // must be used as the username. If you need to work around this, provide
        // your own implementation of UserDetails which has both apiKey (principal)
        // and your username as member fields.
        return new User(apiKey, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
