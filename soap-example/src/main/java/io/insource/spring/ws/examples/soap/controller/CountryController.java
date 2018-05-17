package io.insource.spring.ws.examples.soap.controller;

import io.insource.spring.ws.examples.soap.service.CountryService;

import io.spring.guides.gs_producing_web_service.Country;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/v1")
public class CountryController {
    private final CountryService countryService;

    @Inject
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public Country getCountry(@RequestParam("name") String name) {
        return countryService.getCountry(name);
    }
}
