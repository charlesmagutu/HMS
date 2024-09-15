package com.condabu.paymentservice.mpesa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mpesa")
@Data
public class MpesaConfig {
    private String consumerKey;
    private String consumerSecret;
    private String baseUrl;
    private String callBack;
    private String passKey;
    private String businessShortCode;
}
