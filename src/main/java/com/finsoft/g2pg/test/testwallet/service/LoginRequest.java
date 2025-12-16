
package com.finsoft.g2pg.test.testwallet.service;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginRequest", propOrder = {
        "username"
})
public class LoginRequest {

    protected String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

}
