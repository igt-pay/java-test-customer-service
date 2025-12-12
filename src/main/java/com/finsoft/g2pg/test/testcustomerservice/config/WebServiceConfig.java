package com.finsoft.g2pg.test.testcustomerservice.config;

import com.finsoft.g2pg.test.testwallet.service.TestWalletService;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

import javax.xml.namespace.QName;
import java.net.URL;

@Configuration
public class WebServiceConfig {

    private static final Logger log = LoggerFactory.getLogger(WebServiceConfig.class);

    private static final String WSDL_PATH = "wsdl/G2PGTestWalletAPI.wsdl";
    private static final String NAMESPACE_URI = "http://com.finsoft.g2pg";
    private static final String SERVICE_NAME = "G2PGTestWalletAPI";
    private static final String PORT_NAME = "TestWalletServicePort";

    @Value("${app.test-wallet.url}")
    private String testWalletUrl;

    @Bean(name = "iTestWalletWebService")
    @ConditionalOnClass(name = "com.finsoft.g2pg.test.testwallet.service.TestWalletService")
    @Lazy
    public TestWalletService createTestWalletService() {
        try {
            log.info("Creating Test Wallet Service client using " + WSDL_PATH);

            ClassPathResource wsdlResource = new ClassPathResource(WSDL_PATH);
            URL wsdlUrl = wsdlResource.getURL();

            QName serviceName = new QName(NAMESPACE_URI, SERVICE_NAME);
            Service service = Service.create(wsdlUrl, serviceName);
            QName portName = new QName(NAMESPACE_URI, PORT_NAME);

            TestWalletService port = service.getPort(portName, TestWalletService.class);

            if (testWalletUrl == null || testWalletUrl.isEmpty()) {
                throw new WebServiceException("Test Wallet URL is not configured");
            }
            BindingProvider bindingProvider = (BindingProvider) port;
            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, testWalletUrl);
            log.info("Test Wallet Service client created successfully with endpoint: {}", testWalletUrl);
            return port;
        } catch (Exception e) {
            log.error("Failed to create Test Wallet Service client", e);
            throw new WebServiceException("Failed to create Test Wallet Service client", e);
        }
    }
}
