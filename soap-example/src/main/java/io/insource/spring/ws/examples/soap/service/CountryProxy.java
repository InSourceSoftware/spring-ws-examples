package io.insource.spring.ws.examples.soap.service;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import io.spring.guides.gs_producing_web_service.ObjectFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.inject.Inject;
import javax.xml.bind.JAXBIntrospector;

@Component
public class CountryProxy {
    /**
     * Web service base URL.
     */
    private static final String BASE_URL = "http://localhost:8888/ws";

    private final WebServiceTemplate webServiceTemplate;
    private final ObjectFactory objectFactory = new ObjectFactory();

    @Inject
    public CountryProxy(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public Country getCountry(String name) {
        GetCountryRequest requestPayload = objectFactory.createGetCountryRequest();
        requestPayload.setName(name);

        return doRequest(requestPayload, GetCountryResponse.class).getCountry();
    }

    private <T> T doRequest(Object requestPayload, Class<T> responseClass) {
        Object jaxbElement = webServiceTemplate.marshalSendAndReceive(BASE_URL, requestPayload);
        return responseClass.cast(JAXBIntrospector.getValue(jaxbElement));
    }
}
