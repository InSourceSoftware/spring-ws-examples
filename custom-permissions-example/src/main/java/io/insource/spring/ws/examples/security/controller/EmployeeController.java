package io.insource.spring.ws.examples.security.controller;

import io.insource.spring.ws.examples.security.model.Employee;
import io.insource.spring.ws.examples.security.repository.EmployeeRepository;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class EmployeeController {
    @Inject
    private EmployeeRepository employeeRepository;

    @GetMapping("/supervisors/{name}/employees")
    public List<Employee> getEmployees(@PathVariable("name") String supervisorName) {
        return employeeRepository.findEmployeesBySupervisor(supervisorName);
    }

    @GetMapping("/supervisors/{name}/employees/{permission}.{ext}")
    @PostFilter("hasPermission(filterObject, #permission + '.' + #ext)")
    public List<Employee> getEmployeesWithPermission(@PathVariable("name") String name, @PathVariable("permission") String permission, @PathVariable("ext") String ext) {
        return employeeRepository.findEmployeesBySupervisor(name);
    }
}
