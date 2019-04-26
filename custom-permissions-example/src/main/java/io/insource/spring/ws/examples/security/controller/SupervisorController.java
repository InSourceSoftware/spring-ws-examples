package io.insource.spring.ws.examples.security.controller;

import io.insource.spring.ws.examples.security.model.Supervisor;
import io.insource.spring.ws.examples.security.repository.SupervisorRepository;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class SupervisorController {
    @Inject
    private SupervisorRepository supervisorRepository;

    @GetMapping("/supervisors")
    public List<Supervisor> getSupervisors() {
        return supervisorRepository.findAll();
    }

    @GetMapping("/supervisors/{name}")
    public Supervisor getSupervisor(@PathVariable("name") String name) {
        return supervisorRepository.findOne(name);
    }

    @PostFilter("hasPermission(filterObject, #permission + '.' + #ext)")
    @GetMapping("/supervisors/search/{permission}.{ext}")
    public List<Supervisor> getEmployeesWithPermission(@PathVariable("permission") String permission, @PathVariable("ext") String ext) {
        return supervisorRepository.findAll();
    }
}
