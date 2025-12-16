package com.finsoft.g2pg.test.testcustomerservice.config;

import com.finsoft.g2pg.test.testcustomerservice.filter.AuthenticationFilter;
import com.finsoft.g2pg.test.testcustomerservice.listener.SessionListener;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistration(AuthenticationFilter authenticationFilter) {
        FilterRegistrationBean<AuthenticationFilter> registration =
                new FilterRegistrationBean<>(authenticationFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
        return registration;
    }
}

