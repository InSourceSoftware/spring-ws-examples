package io.insource.spring.ws.examples.security.config;

import io.insource.spring.ws.examples.security.permissions.PermissionEvaluatorManager;
import io.insource.spring.ws.examples.security.permissions.TargetedPermissionEvaluator;
import io.insource.spring.ws.examples.security.permissions.impl.EmployeePermissionEvaluator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import javax.inject.Inject;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    private final List<TargetedPermissionEvaluator> permissionEvaluators;

    @Inject
    public MethodSecurityConfiguration(List<TargetedPermissionEvaluator> permissionEvaluators) {
        this.permissionEvaluators = permissionEvaluators;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        methodSecurityExpressionHandler.setPermissionEvaluator(permissionEvaluator());

        return methodSecurityExpressionHandler;
    }

    @Bean
    @Primary
    public PermissionEvaluator permissionEvaluator() {
        return new PermissionEvaluatorManager(permissionEvaluators);
    }
}
