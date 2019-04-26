package io.insource.spring.ws.examples.security.repository;

import io.insource.spring.ws.examples.security.model.Employee;
import io.insource.spring.ws.examples.security.permissions.Permissions;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Component
public class EmployeeRepository {
    private static final Map<String, List<Employee>> EMPLOYEES_BY_SUPERVISOR;
    private static final Map<String, Employee> EMPLOYEES;

    static {
        Employee employee1 = new Employee("Nancy");
        Employee employee2 = new Employee("Nguyen");
        Employee employee3 = new Employee("Jim");
        Employee employee4 = new Employee("Bob");
        Employee employee5 = new Employee("John");

        employee1.getPermissions().add(Permissions.WORK_FROM_HOME_ALLOWED);
        employee1.getPermissions().add(Permissions.CORPORATE_SPEND_ACCOUNT_ALLOWED);
        employee2.getPermissions().add(Permissions.WORK_FROM_HOME_ALLOWED);
        employee2.getPermissions().add(Permissions.MILEAGE_REIMBURSEMENT_ALLOWED);
        employee3.getPermissions().add(Permissions.WORK_FROM_HOME_ALLOWED);
        employee4.getPermissions().add(Permissions.WORK_FROM_HOME_ALLOWED);
        employee4.getPermissions().add(Permissions.CORPORATE_SPEND_ACCOUNT_ALLOWED);
        employee4.getPermissions().add(Permissions.MILEAGE_REIMBURSEMENT_ALLOWED);

        EMPLOYEES_BY_SUPERVISOR = Collections.unmodifiableMap(new HashMap<String, List<Employee>>() {{
            put("Sally", new ArrayList<>(asList(employee1, employee2)));
            put("Fred", new ArrayList<>(asList(employee3, employee4, employee5)));
        }});

        EMPLOYEES = Collections.unmodifiableMap(new HashMap<String, Employee>() {{
            put(employee1.getName(), employee1);
            put(employee2.getName(), employee2);
            put(employee3.getName(), employee3);
            put(employee4.getName(), employee4);
            put(employee5.getName(), employee5);
        }});
    }

    public Employee findOne(String name) {
        return EMPLOYEES.get(name);
    }

    public List<Employee> findBySupervisorName(String name) {
        return new ArrayList<>(EMPLOYEES_BY_SUPERVISOR.getOrDefault(name, Collections.emptyList()));
    }
}
