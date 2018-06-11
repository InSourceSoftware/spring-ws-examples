package io.insource.spring.ws.examples.security.model;

import java.util.List;

public class Supervisor {
    private String name;
    private List<Employee> employees;

    public Supervisor() {
    }

    public Supervisor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
