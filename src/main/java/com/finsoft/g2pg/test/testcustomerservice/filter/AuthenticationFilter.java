package com.finsoft.g2pg.test.testcustomerservice.filter;

import com.finsoft.g2pg.test.testcustomerservice.domain.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationFilter implements Filter {

    private static final String LOGIN_PAGE = "/login";
    private static final String INDEX_PAGE = "/";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        
        String pathWithinContext = requestURI.substring(contextPath.length());
        if (!pathWithinContext.startsWith("/")) {
            pathWithinContext = "/" + pathWithinContext;
        }

        if (pathWithinContext.equals("/") ||
                pathWithinContext.equals("/login") ||
                pathWithinContext.startsWith("/css/") ||
                pathWithinContext.startsWith("/js/") ||
                pathWithinContext.startsWith("/images/") ||
                pathWithinContext.startsWith("/error")) {
            chain.doFilter(request, response);
            return;
        }

        if (httpRequest.isRequestedSessionIdValid() && User.existsInSession(session)) {
            chain.doFilter(request, response);
        } else {
            if (isSessionInvalid(httpRequest)) {
                httpResponse.sendRedirect(contextPath + INDEX_PAGE);
            } else {
                httpResponse.sendRedirect(contextPath + LOGIN_PAGE);
            }
        }
    }

    private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
        return (httpServletRequest.getRequestedSessionId() != null)
                && !httpServletRequest.isRequestedSessionIdValid();
    }
}

