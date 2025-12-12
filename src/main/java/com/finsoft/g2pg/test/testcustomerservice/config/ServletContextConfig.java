package com.finsoft.g2pg.test.testcustomerservice.config;

import com.finsoft.g2pg.test.testcustomerservice.listener.SessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletContextConfig {

    @Bean
    public ServletListenerRegistrationBean<SessionListener> sessionListenerRegistration() {
        ServletListenerRegistrationBean<SessionListener> registration = 
            new ServletListenerRegistrationBean<>();
        registration.setListener(new SessionListener());
        return registration;
    }
}

