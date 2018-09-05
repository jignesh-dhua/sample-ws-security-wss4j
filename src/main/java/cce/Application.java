package cce;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import cce.client.WSClient;
import nl.kvk.schemas.schemas.hrip.dataservice._2015._02.InschrijvingRequestType;
import nl.kvk.schemas.schemas.hrip.dataservice._2015._02.InschrijvingResponseType;

@SpringBootApplication
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    public static final String NAMESPACE_URI = "http://schemas.kvk.nl/schemas/hrip/dataservice/2015/02";

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
    @Value("${client.ws.user}")
    String authenticatedUserId;

//    @Value("${client.ws.reply}")
//    String replyURI;

//    @Value("${client.ws.docketId}")
//    String docketId;
//
//    @Value("${client.ws.trackingId}")
//    String trackingId;

    @Bean
    CommandLineRunner lookup(@Autowired WSClient wsclient) {
        return args -> {
        	InschrijvingRequestType request = new InschrijvingRequestType();
            // Request metadata
            //request.setRequestMetadata(authenticatedUserId, UUID.randomUUID().toString(), replyURI);
        	
        	//request.setKvkNummer(value);;
        	request.setKlantreferentie("SoapUI-TEST");
        	request.setRsin("992353427");
        	

            LOG.info("\n\n" + request + "\n");

            QName qname = new QName(NAMESPACE_URI, "RequestCourtCaseEvent");
            JAXBElement<InschrijvingResponseType> responseJAXBElement = wsclient.queryByDocketId(new JAXBElement(qname, InschrijvingResponseType.class, request));
            InschrijvingResponseType response = responseJAXBElement.getValue();

            LOG.info("\n\n" + response + "\n");
        };
    }

}
