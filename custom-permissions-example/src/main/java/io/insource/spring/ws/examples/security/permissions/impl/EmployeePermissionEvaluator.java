package io.insource.spring.ws.examples.security.permissions.impl;

import io.insource.spring.ws.examples.security.model.Employee;
import io.insource.spring.ws.examples.security.permissions.TargetedPermissionEvaluator;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class EmployeePermissionEvaluator implements TargetedPermissionEvaluator {
    @Override
    public String getTargetType() {
        return Employee.class.getSimpleName();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject != null && Employee.class.isAssignableFrom(targetDomainObject.getClass())) {
            Employee domainObject = (Employee) targetDomainObject;
            return domainObject.getPermissions().contains(permission.toString());
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new UnsupportedOperationException("Not supported by this PermissionEvaluator: " + EmployeePermissionEvaluator.class);
    }
}
