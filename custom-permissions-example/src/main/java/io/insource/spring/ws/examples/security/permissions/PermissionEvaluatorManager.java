package io.insource.spring.ws.examples.security.permissions;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionEvaluatorManager implements PermissionEvaluator {
    private static final PermissionEvaluator denyAll = new DenyAllPermissionEvaluator();
    private final Map<String, TargetedPermissionEvaluator> permissionEvaluators;

    public PermissionEvaluatorManager(List<TargetedPermissionEvaluator> permissionEvaluators) {
        this.permissionEvaluators = new HashMap<>();
        for (TargetedPermissionEvaluator permissionEvaluator : permissionEvaluators) {
            this.permissionEvaluators.put(permissionEvaluator.getTargetType(), permissionEvaluator);
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        PermissionEvaluator permissionEvaluator = permissionEvaluators.get(targetDomainObject.getClass().getSimpleName());
        if (permissionEvaluator == null) {
            permissionEvaluator = denyAll;
        }

        return permissionEvaluator.hasPermission(authentication, targetDomainObject, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        PermissionEvaluator permissionEvaluator = permissionEvaluators.get(targetType);
        if (permissionEvaluator == null) {
            permissionEvaluator = denyAll;
        }

        return permissionEvaluator.hasPermission(authentication, targetId, targetType, permission);
    }
}
