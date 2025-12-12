
package com.finsoft.g2pg.test.testwallet.service;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private static final QName _LoginRequest_QNAME = new QName("http://com.finsoft.g2pg", "loginRequest");
    private static final QName _LoginResponse_QNAME = new QName("http://com.finsoft.g2pg", "loginResponse");

    public ObjectFactory() {
    }

    public LoginRequest createLoginRequest() {
        return new LoginRequest();
    }

    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    @XmlElementDecl(namespace = "http://com.finsoft.g2pg", name = "loginRequest")
    public JAXBElement<LoginRequest> createLoginRequest(LoginRequest value) {
        return new JAXBElement<>(_LoginRequest_QNAME, LoginRequest.class, null, value);
    }

    @XmlElementDecl(namespace = "http://com.finsoft.g2pg", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

}
