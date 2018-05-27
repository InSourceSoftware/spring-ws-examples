package io.insource.spring.ws.examples.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        BasicAuthProperties properties = basicAuthProperties();
        http.requestMatchers().antMatchers(properties.getPath())
            .and()
                .authorizeRequests()
                    .antMatchers("/", "/auth/*").permitAll()
                    .anyRequest().authenticated()
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .httpBasic().realmName(properties.getRealm())
            .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BasicAuthProperties properties = basicAuthProperties();
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> builder = auth.inMemoryAuthentication();
        for (SecurityProperties.User user : properties.getUsers()) {
            builder.withUser(user.getName())
                .password(user.getPassword())
                .roles(user.getRole().toArray(new String[0]));
        }
    }

    @Bean
    public BasicAuthProperties basicAuthProperties() {
        return new BasicAuthProperties();
    }
}
