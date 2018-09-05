package cce.client;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import nl.kvk.schemas.schemas.hrip.dataservice._2015._02.InschrijvingResponseType;

public class WSClient extends WebServiceGatewaySupport {

    public JAXBElement<InschrijvingResponseType> queryByDocketId(JAXBElement<InschrijvingResponseType> request){
        return (JAXBElement<InschrijvingResponseType>) getWebServiceTemplate()
                .marshalSendAndReceive(request);

    }
}