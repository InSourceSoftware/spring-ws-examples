package io.insource.spring.ws.examples.security.permissions.impl;

import io.insource.spring.ws.examples.security.model.Employee;
import io.insource.spring.ws.examples.security.model.Supervisor;
import io.insource.spring.ws.examples.security.permissions.TargetedPermissionEvaluator;
import io.insource.spring.ws.examples.security.repository.SupervisorRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;

@Component
public class SupervisorPermissionEvaluator implements TargetedPermissionEvaluator {
    private final SupervisorRepository supervisorRepository;

    @Inject
    public SupervisorPermissionEvaluator(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    @Override
    public String getTargetType() {
        return Supervisor.class.getSimpleName();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject != null && Supervisor.class.isAssignableFrom(targetDomainObject.getClass())) {
            Supervisor domainObject = (Supervisor) targetDomainObject;
            for (Employee child : domainObject.getEmployees()) {
                if (child.getPermissions().contains(permission.toString())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        Supervisor domainObject = supervisorRepository.findOne((String) targetId);
        return hasPermission(authentication, domainObject, permission);
    }
}
