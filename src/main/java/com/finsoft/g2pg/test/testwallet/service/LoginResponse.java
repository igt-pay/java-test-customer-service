
package com.finsoft.g2pg.test.testwallet.service;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginResponse", propOrder = {
        "tempSessionId"
})
public class LoginResponse {

    protected String tempSessionId;

    public String getTempSessionId() {
        return tempSessionId;
    }

    public void setTempSessionId(String value) {
        this.tempSessionId = value;
    }

}
