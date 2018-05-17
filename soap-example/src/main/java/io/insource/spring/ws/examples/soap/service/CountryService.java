package io.insource.spring.ws.examples.soap.service;

import io.spring.guides.gs_producing_web_service.Country;

public interface CountryService {
    Country getCountry(String name);
}
