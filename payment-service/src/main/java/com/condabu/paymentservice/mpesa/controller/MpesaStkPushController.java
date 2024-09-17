package com.condabu.paymentservice.mpesa.controller;

import com.condabu.paymentservice.mpesa.dto.MpesaStkPushRequest;
import com.condabu.paymentservice.mpesa.service.MpesaService;
import com.condabu.paymentservice.mpesa.service.MpesaTransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment/mpesa")
public class MpesaStkPushController {

    private static final Logger log = LoggerFactory.getLogger(MpesaStkPushController.class);
    @Autowired
    private  MpesaService mpesaService;
    @Autowired
    private MpesaTransactionService mpesaTransactionService;
    public MpesaStkPushController(MpesaService mpesaService) {
        this.mpesaService = mpesaService;
    }


    @PostMapping("/stkpush")
    public ResponseEntity<String> initiateStkPush(@Valid @RequestBody MpesaStkPushRequest requestDto) {
        String response = String.valueOf(mpesaService.intiateStkPush(requestDto));
        return ResponseEntity.ok(response);
    }


    @PostMapping("/callback")
    public ResponseEntity<String> callbackS(@RequestBody Map<String, Object> callBackData){
        log.info("Received callback from MPesa: {}", callBackData);

        try {
            // Delegate the logic to the service
            String result = String.valueOf(mpesaTransactionService.handleCallback(callBackData));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Error processing callback: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
