package com.condabu.paymentservice.mpesa.service;

import com.condabu.paymentservice.mpesa.config.MpesaConfig;
import com.condabu.paymentservice.mpesa.dto.MpesaTokenResponse;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MpesaTokenService {
    private final RestTemplate restTemplate;
    private final MpesaConfig mpesaConfig;
    private final Cache<String, String> tokenCache;



    public MpesaTokenService(RestTemplate restTemplate, MpesaConfig mpesaConfig) {
        this.restTemplate = restTemplate;
        this.mpesaConfig = mpesaConfig;
        this.tokenCache = Caffeine.newBuilder()
                .expireAfterWrite(55, TimeUnit.MINUTES)
                .build();
    }

    public String getAccessToken(){
        return tokenCache.get("access_token", key-> fetchAccessToken());
    }

    private String fetchAccessToken(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(mpesaConfig.getConsumerKey(), mpesaConfig.getConsumerSecret());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<MpesaTokenResponse> response = restTemplate
                .exchange(mpesaConfig.getBaseUrl()+"/oauth/v1/generate?grant_type=client_credentials", HttpMethod.GET
                ,entity, MpesaTokenResponse.class);

        String access_token = Objects.requireNonNull(response.getBody()).getAccess_token();
        log.info("mpesa_token access token: {}", response.getBody());
        return access_token;
    }
}