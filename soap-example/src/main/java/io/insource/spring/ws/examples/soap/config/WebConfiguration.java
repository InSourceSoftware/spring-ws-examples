package io.insource.spring.ws.examples.soap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;

@Configuration
public class WebConfiguration {
    @Bean
    public SaajSoapMessageFactory saajSoapMessageFactory() throws SOAPException {
        return new SaajSoapMessageFactory(MessageFactory.newInstance());
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setPackagesToScan("io.spring.guides.gs_producing_web_service");

        return jaxb2Marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(SaajSoapMessageFactory saajSoapMessageFactory, Jaxb2Marshaller jaxb2Marshaller) {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate(saajSoapMessageFactory);
        webServiceTemplate.setDefaultUri("http://localhost:8888/ws");
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);

        return webServiceTemplate;
    }
}
