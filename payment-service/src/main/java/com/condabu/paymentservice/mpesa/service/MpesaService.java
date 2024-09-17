package com.condabu.paymentservice.mpesa.service;

import com.condabu.paymentservice.mpesa.config.MpesaConfig;
import com.condabu.paymentservice.mpesa.dto.MpesaStkPushRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MpesaService {

    private final RestTemplate restTemplate;
    private final MpesaTokenService mpesaTokenService;
    private final MpesaConfig mpesaConfig;

    public MpesaService(RestTemplate restTemplate, MpesaTokenService mpesaTokenService, MpesaConfig mpesaConfig) {
        this.restTemplate = restTemplate;
        this.mpesaTokenService = mpesaTokenService;
        this.mpesaConfig = mpesaConfig;
    }

    public ResponseEntity<String> intiateStkPush(MpesaStkPushRequest mpesaStkPushRequest) {
        log.info("Preparing to send STK push request with details: PhoneNumber={}, Amount={}",
                mpesaStkPushRequest.getPhoneNumber(), mpesaStkPushRequest.getAmount());

        String token = mpesaTokenService.getAccessToken();
        log.info("Encoded password {}", token);

        Map<String, Object> stkRequest = createStkPushPayload(mpesaStkPushRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(stkRequest, httpHeaders);

        String url = mpesaConfig.getBaseUrl() + "/mpesa/stkpush/v1/processrequest";

        // Log the full URL and the payload
        log.info("Sending STK push request to URL: {}", url);
        log.info("Payload: {}", stkRequest);

        // Send the request and log the response
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        log.info("Response Body: {}", response.getBody());

        return response;
    }

    private Map<String, Object> createStkPushPayload(MpesaStkPushRequest mpesaStkPushRequest) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("BusinessShortCode", mpesaConfig.getBusinessShortCode());
        payload.put("Password", generatePassword());
        payload.put("Timestamp", generateTimeStamp());
        payload.put("TransactionType", "CustomerPayBillOnline");
        payload.put("Amount", mpesaStkPushRequest.getAmount());
        payload.put("PartyA", mpesaStkPushRequest.getPhoneNumber());
        payload.put("PartyB", mpesaConfig.getBusinessShortCode());
        payload.put("PhoneNumber", mpesaStkPushRequest.getPhoneNumber());
        payload.put("CallBackURL", mpesaConfig.getCallBack());
        payload.put("AccountReference", mpesaStkPushRequest.getAccountReference());
        payload.put("TransactionDesc", mpesaStkPushRequest.getTransactionDesc());

        log.warn("Payload sent {}", payload);
        return payload;
    }

    private String generatePassword() {
        String toEncode = mpesaConfig.getBusinessShortCode() + mpesaConfig.getPassKey() + generateTimeStamp();
        String encodedPassword = Base64.getEncoder().encodeToString(toEncode.getBytes());
        log.info("Encoded password: {}", encodedPassword);
        return encodedPassword;
    }

    private String generateTimeStamp() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(dateFormat);
    }

}
