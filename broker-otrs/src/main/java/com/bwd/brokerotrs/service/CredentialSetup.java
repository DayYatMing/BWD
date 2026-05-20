package com.bwd.brokerotrs.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CredentialSetup {

    public String getCredentials(Environment env){
        return "?UserLogin=" +
                env.getProperty("otrs.username") +
                "&Password=" + env.getProperty("otrs.password");
    }

}
