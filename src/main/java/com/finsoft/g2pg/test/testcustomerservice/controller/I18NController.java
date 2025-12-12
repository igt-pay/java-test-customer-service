package com.finsoft.g2pg.test.testcustomerservice.controller;

import com.finsoft.g2pg.test.testcustomerservice.domain.Language;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Component
@SessionScope
public class I18NController {

    private Locale currentLocale;
    private String languageId;
    private Map<String, Language> languages;

    private final MessageSource messageSource;
    private final List<Locale> supportedLocales;

    public I18NController(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.supportedLocales = Arrays.asList(
                Locale.ENGLISH,
                Locale.GERMAN,
                Locale.forLanguageTag("es"),
                Locale.FRENCH,
                Locale.forLanguageTag("nl")
        );
        initialize();
    }

    public void initialize() {
        Map<String, Language> langs = new LinkedHashMap<>();

        for (Locale locale : supportedLocales) {
            Language lang = new Language();
            lang.setId(locale.getLanguage());
            try {
                String description = messageSource.getMessage(
                        "pp.language." + locale.getLanguage(),
                        null,
                        locale);
                lang.setDescription(description);
            } catch (Exception e) {
                lang.setDescription(locale.getDisplayLanguage());
            }
            langs.put(lang.getId(), lang);
        }

        setLanguages(langs);

        Locale clientLocale = LocaleContextHolder.getLocale();
        if (langs.containsKey(clientLocale.getLanguage())) {
            setCurrentLocale(clientLocale);
        } else {
            setCurrentLocale(Locale.ENGLISH);
        }
        languageId = getCurrentLocale().getLanguage();
    }

    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
        LocaleContextHolder.setLocale(currentLocale);
    }

    public Locale getCurrentLocale() {
        return currentLocale != null ? currentLocale : Locale.ENGLISH;
    }

    public void setLanguages(Map<String, Language> languages) {
        this.languages = languages;
    }

    public Map<String, Language> getLanguages() {
        return languages;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
        if (languageId != null) {
            Locale newLocale = Locale.forLanguageTag(languageId);
            setCurrentLocale(newLocale);
        }
    }

    public String getLanguageId() {
        return languageId != null ? languageId : getCurrentLocale().getLanguage();
    }

    public boolean isShowLanguages() {
        return getLanguages() != null && getLanguages().size() > 1;
    }

}

