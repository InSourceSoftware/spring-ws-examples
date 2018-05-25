package io.insource.spring.ws.examples.apigateway.config;

import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
            .antMatchers("/login**").permitAll()
            .antMatchers("/**").authenticated()
        .and()
            .anonymous().principal("anonymous").authorities("ROLE_ANONYMOUS")
        .and()
            .formLogin().successHandler(authenticationSuccessHandler())
        .and()
            .logout().logoutSuccessUrl("/")
        .and()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/");
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new Http401AuthenticationEntryPoint("MyRealm");
    }
}
