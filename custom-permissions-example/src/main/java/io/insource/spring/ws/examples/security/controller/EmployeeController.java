package io.insource.spring.ws.examples.security.controller;

import io.insource.spring.ws.examples.security.model.Employee;
import io.insource.spring.ws.examples.security.permissions.Permissions;
import io.insource.spring.ws.examples.security.repository.EmployeeRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {
    @Inject
    private EmployeeRepository employeeRepository;

    @GetMapping("/supervisors/{name}/employees")
    public List<Employee> getEmployees(@PathVariable("name") String supervisorName) {
        return employeeRepository.findBySupervisorName(supervisorName);
    }

    @PostFilter("hasPermission(filterObject, #permission + '.' + #ext)")
    @GetMapping("/supervisors/{name}/employees/{permission}.{ext}")
    public List<Employee> getEmployeesWithPermission(@PathVariable("name") String name, @PathVariable("permission") String permission, @PathVariable("ext") String ext) {
        return employeeRepository.findBySupervisorName(name);
    }

    @PreAuthorize("hasPermission(#report['name'], 'Employee', '" + Permissions.MILEAGE_REIMBURSEMENT_ALLOWED + "')")
    @PostMapping("/employees/expense-report")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void submitExpenseReport(@RequestBody Map<String, String> report) {
        // Do something with expense report data...
    }
}
