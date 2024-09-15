package com.condabu.paymentservice.mpesa.dto;


import lombok.Data;

@Data
public class MpesaTokenResponse {
    private String access_token;
    private String expires_in;
}
