package com.condabu.paymentservice.mpesa.dto;


import lombok.Data;

@Data
public class MpesaStkPushRequest {
    private String phoneNumber;
    private String amount;
    private String accountReference;
    private String transactionDesc;
}
