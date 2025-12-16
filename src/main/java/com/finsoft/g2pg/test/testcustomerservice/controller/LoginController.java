package com.finsoft.g2pg.test.testcustomerservice.controller;

import com.finsoft.g2pg.test.testcustomerservice.constants.ReturnCodes;
import com.finsoft.g2pg.test.testcustomerservice.domain.User;
import com.finsoft.g2pg.test.testcustomerservice.service.ITestWalletServiceImpl;
import com.finsoft.g2pg.test.testcustomerservice.util.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private static final long WAIT_TIMEMILLIS = 1000L;

    @Value("${app.version}")
    private String appVersion;
    private final ITestWalletServiceImpl testWalletService;
    private final MessageSource messageSource;
    private final I18NController i18nController;

    public LoginController(ITestWalletServiceImpl testWalletService, MessageSource messageSource, I18NController i18nController) {
        this.testWalletService = testWalletService;
        this.messageSource = messageSource;
        this.i18nController = i18nController;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session,
                            @RequestParam(required = false) String lang) {
        if (User.getFromSession(session) != null) {
            return "redirect:/home";
        }

        I18NController controller = (I18NController) session.getAttribute("controller");
        if (controller == null && i18nController != null) {
            controller = i18nController;
            session.setAttribute("controller", controller);
        }

        if (lang != null && controller != null) {
            controller.setLanguageId(lang);
        }

        model.addAttribute("appVersion", "v"+appVersion);
        if (controller != null) {
            model.addAttribute("controller", controller);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(required = false) String username,
            HttpServletRequest request,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Locale locale) {

        String outcome = ReturnCodes.EMPTY;

        if (!Util.isNullOrEmpty(username)) {
            Object controllerObj = session.getAttribute("controller");
            session.invalidate();
            HttpSession newSession = request.getSession(true);
            if (controllerObj != null) {
                newSession.setAttribute("controller", controllerObj);
            }

            String tempSessId = testWalletService.login(username);
            if (Util.isNullOrEmpty(tempSessId)) {
                outcome = ReturnCodes.FAILURE;
                try {
                    String errorMessage = messageSource.getMessage(
                            "pp.message.invalid_login", null, locale);
                    redirectAttributes.addFlashAttribute("error", errorMessage);
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("error", "Invalid login.");
                }

                try {
                    Thread.sleep(WAIT_TIMEMILLIS);
                } catch (Exception ignored) {
                }
            } else {
                User user = new User();
                user.setUsername(username);
                User.putInSession(newSession, user);
                newSession.setAttribute("tempSessId", tempSessId);
                if (i18nController != null) {
                    newSession.setAttribute("controller", i18nController);
                    newSession.setAttribute("languageId", i18nController.getLanguageId());
                }
                outcome = ReturnCodes.SUCCESS;
            }
        }

        log.info("login() - outcome = {}", outcome);

        if (ReturnCodes.SUCCESS.equals(outcome)) {
            return "redirect:/home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User.removeFromSession(session);
        session.invalidate();
        return "redirect:/login";
    }

}
