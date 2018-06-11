package io.insource.spring.ws.examples.security.repository;

import io.insource.spring.ws.examples.security.model.Supervisor;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

@Component
public class SupervisorRepository {
    @Inject
    private EmployeeRepository employeeRepository;

    public List<Supervisor> findSupervisors() {
        Supervisor supervisor1 = new Supervisor("Sally");
        Supervisor supervisor2 = new Supervisor("Fred");

        supervisor1.setEmployees(employeeRepository.findEmployeesBySupervisor(supervisor1.getName()));
        supervisor2.setEmployees(employeeRepository.findEmployeesBySupervisor(supervisor2.getName()));

        return new ArrayList<>(asList(
            supervisor1,
            supervisor2
        ));
    }

    public Supervisor findSupervisor(String name) {
        Supervisor supervisor = new Supervisor(name);
        supervisor.setEmployees(employeeRepository.findEmployeesBySupervisor(name));

        return supervisor;
    }
}
