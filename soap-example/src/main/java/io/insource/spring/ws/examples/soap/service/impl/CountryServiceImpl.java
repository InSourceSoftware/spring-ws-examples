package io.insource.spring.ws.examples.soap.service.impl;

import io.insource.spring.ws.examples.soap.service.CountryProxy;
import io.insource.spring.ws.examples.soap.service.CountryService;

import io.spring.guides.gs_producing_web_service.Country;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryProxy countryProxy;

    @Inject
    public CountryServiceImpl(CountryProxy countryProxy) {
        this.countryProxy = countryProxy;
    }

    @Override
    public Country getCountry(String name) {
        return countryProxy.getCountry(name);
    }
}
