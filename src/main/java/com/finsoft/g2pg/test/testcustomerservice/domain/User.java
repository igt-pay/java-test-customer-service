package com.finsoft.g2pg.test.testcustomerservice.domain;

import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String PORTAL_USER = "com.finsoft.g2pg.test.testcustomerservice.domain.User";

    private String username;
    private String displayName;
    private String currencyId;
    private String languageId;
    private String customerId;
    private String accountType;
    private String systemAccountType;
    private String accountNumber;
    private BigDecimal accountId;
    private HashMap<String, String> privileges;

    public User() {
    }

    public static User getFromSession(HttpSession session) {
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute(PORTAL_USER);
    }

    public static void putInSession(HttpSession session, User user) {
        if (session != null && user != null) {
            session.setAttribute(PORTAL_USER, user);
        }
    }

    public static boolean existsInSession(HttpSession session) {
        return session != null && session.getAttribute(PORTAL_USER) != null;
    }

    public static void removeFromSession(HttpSession session) {
        if (session != null) {
            session.removeAttribute(PORTAL_USER);
        }
    }

    public BigDecimal getAccountId() {
        return accountId;
    }

    public void setAccountId(BigDecimal accountId) {
        this.accountId = accountId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getSystemAccountType() {
        return systemAccountType;
    }

    public void setSystemAccountType(String systemAccountType) {
        this.systemAccountType = systemAccountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean hasPrivilege(String key) {
        return key == null || (privileges != null && privileges.containsKey(key));
    }

    public void setPrivileges(HashMap<String, String> privileges) {
        this.privileges = privileges;
    }

    public HashMap<String, String> getPrivileges() {
        return privileges;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o == this) {
            result = true;
        } else if (o instanceof User user) {
            result = user.username != null && user.username.equals(username);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
