package io.insource.spring.ws.examples.security.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String name;
    private List<String> permissions = new ArrayList<>();

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
