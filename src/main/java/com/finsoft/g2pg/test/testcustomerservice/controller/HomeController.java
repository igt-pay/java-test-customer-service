package com.finsoft.g2pg.test.testcustomerservice.controller;

import com.finsoft.g2pg.test.testcustomerservice.domain.User;
import com.finsoft.g2pg.test.testcustomerservice.util.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Value("${app.customer-service.url}")
    private String customerServiceUrl;

    @Value("${app.customer-service.partner-code}")
    private String partnerCode;

    @Value("${app.customer-service.customer-id:}")
    private String customerId;

    @Value("${app.customer-service.account-number:}")
    private String accountNumber;

    @Value("${app.customer-service.account-type:}")
    private String accountType;

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request, HttpSession session) {
        User user = User.getFromSession(session);
        if (user == null) {
            return "redirect:/login";
        }

        String customerServiceUrl = loadCustomerServiceUrl(session);
        model.addAttribute("customerServiceUrl", customerServiceUrl);

        log.info("customerServiceUrl: {}", customerServiceUrl);
        return "home";
    }

    private String loadCustomerServiceUrl(HttpSession session) {
        String baseUrl = customerServiceUrl;
        URI baseUri;
        try {
            baseUri = URI.create(baseUrl);
        } catch (Exception ex) {
            log.error("Error parsing config URL: {}", baseUrl, ex);
            return "";
        }
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUri(baseUri)
                .queryParam("tempSessId", getTempSessId(session))
                .queryParam("languageId", getLanguageId(session))
                .queryParam("partnerCode", partnerCode);

        if (!Util.isNullOrEmpty(customerId)) {
            builder.queryParam("customerId", customerId);
        }

        if (!Util.isNullOrEmpty(accountNumber)) {
            builder.queryParam("accountNumber", accountNumber);
        }

        if (!Util.isNullOrEmpty(accountType)) {
            builder.queryParam("accountType", accountType);
        }
        return builder.toUriString();
    }

    private String getLanguageId(HttpSession session) {
        if (session != null) {
            I18NController controller = (I18NController) session.getAttribute("controller");
            if (controller != null && controller.getLanguageId() != null) {
                return controller.getLanguageId();
            }
        }
        return "en";
    }

    private String getTempSessId(HttpSession session) {
        return (String) session.getAttribute("tempSessId");
    }

}

