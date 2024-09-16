package org.condabu.notificationservice.Config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.annotation.PostExchange;

@Configuration
public class TwilioConfig {
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @PostConstruct
    public void init(){
        Twilio.init(accountSid, authToken);
        System.out.println("Twilio initialized with account SID: "+ accountSid);
    }
}
