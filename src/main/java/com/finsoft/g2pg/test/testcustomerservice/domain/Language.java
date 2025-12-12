package com.finsoft.g2pg.test.testcustomerservice.domain;

import java.io.Serializable;

public class Language implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String id;
    private String description;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}

