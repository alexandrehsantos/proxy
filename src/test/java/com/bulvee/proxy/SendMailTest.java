package com.bulvee.proxy;

import com.bulvee.proxy.email.MandrilProxy;
import com.bulvee.proxy.helper.MandrillHelpers;
import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SendMailTest {

    private MandrillHelpers mandrillHelpers;
    private MandrilProxy mandrilProxy;

    @BeforeEach
    void setup() {
        this.mandrillHelpers = new MandrillHelpers("alexandrehsantos@gmail.com", "alehss@gmail.com", "hsale@live.com");
        this.mandrilProxy = new MandrilProxy("alexandrehsantos@gmail.com", "alehss@gmail.com", "hsale@live.com");
    }

    @Test
    void givenEmails_whenSendByHelper_valid_thenVerify() {
        try {
            mandrillHelpers.sendMessage("message");
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void givenEmails_whenSendByProxy_valid_thenVerify(){
        try {
            this.mandrilProxy.sendMessage("Teste Message");
            Assertions.assertTrue(verifyEmailIndexDoesNotExist("alehss@gmail.com"));;
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }

    private boolean verifyEmailIndexDoesNotExist(String email) {
        return mandrilProxy.emails.indexOf(email) == -1;
    }

}
