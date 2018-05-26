package io.insource.spring.ws.examples.security.config;

import io.insource.spring.ws.examples.security.service.SimpleUserDetailsService;

import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@Order(-1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .addFilterAfter(preAuthenticationFilter(), RequestHeaderAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/**/public/**").permitAll()
                // TODO: Add matchers here to apply authorization globally, e.g.
                //.antMatchers("**/auth/**").hasRole("ANONYMOUS")
                .antMatchers("/**").authenticated()
            .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    }

    @Bean
    public RequestHeaderAuthenticationFilter preAuthenticationFilter() {
        RequestHeaderAuthenticationFilter preAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        preAuthenticationFilter.setPrincipalRequestHeader("SM_USER");
        preAuthenticationFilter.setAuthenticationManager(authenticationManager());
        preAuthenticationFilter.setExceptionIfHeaderMissing(false);

        return preAuthenticationFilter;
    }

    @Override
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
        authenticationProvider.setThrowExceptionWhenTokenRejected(false);

        return authenticationProvider;
    }

    @Bean
    public UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() {
        return new UserDetailsByNameServiceWrapper<>(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new SimpleUserDetailsService();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new Http401AuthenticationEntryPoint("MyRealm");
    }
}
