package com.finsoft.g2pg.test.testcustomerservice.service;

import com.finsoft.g2pg.test.testwallet.service.LoginRequest;
import com.finsoft.g2pg.test.testwallet.service.LoginResponse;
import com.finsoft.g2pg.test.testwallet.service.TestWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ITestWalletServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(ITestWalletServiceImpl.class);

    private final TestWalletService service;

    @Autowired
    public ITestWalletServiceImpl(@Qualifier("iTestWalletWebService") TestWalletService service) {
        this.service = service;
        log.info("Creating {}", this.getClass().getName());
    }

    public String login(String username, String password) {
        log.info("Test Wallet login({}, xxxxx)", username);

        try {
            LoginRequest req = new LoginRequest();
            req.setUsername(username);
            req.setPassword(password);

            LoginResponse res = service.login(req);

            String tempSessId = (res != null && res.getTempSessionId() != null)
                    ? res.getTempSessionId()
                    : null;

            if (tempSessId != null) {
                log.info("Test Wallet login successful for user: {}", username);
            } else {
                log.warn("Test Wallet login failed - no session ID returned for user: {}", username);
            }

            return tempSessId;
        } catch (Exception e) {
            log.debug("Test Wallet login error: ", e);
            return null;
        }
    }
}

