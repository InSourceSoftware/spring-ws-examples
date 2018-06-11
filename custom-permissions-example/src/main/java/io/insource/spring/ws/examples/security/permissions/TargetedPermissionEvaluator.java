package io.insource.spring.ws.examples.security.permissions;

import org.springframework.security.access.PermissionEvaluator;

public interface TargetedPermissionEvaluator extends PermissionEvaluator {
    String getTargetType();
}
