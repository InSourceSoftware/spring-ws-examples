package io.insource.spring.ws.examples.security.permissions.impl;

import io.insource.spring.ws.examples.security.model.Employee;
import io.insource.spring.ws.examples.security.permissions.TargetedPermissionEvaluator;
import io.insource.spring.ws.examples.security.repository.EmployeeRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;

@Component
public class EmployeePermissionEvaluator implements TargetedPermissionEvaluator {
    private final EmployeeRepository employeeRepository;

    @Inject
    public EmployeePermissionEvaluator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

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
        if (targetType.equals(getTargetType())) {
            Employee domainObject = employeeRepository.findOne((String) targetId);
            return domainObject.getPermissions().contains(permission.toString());
        }

        return false;
    }
}
