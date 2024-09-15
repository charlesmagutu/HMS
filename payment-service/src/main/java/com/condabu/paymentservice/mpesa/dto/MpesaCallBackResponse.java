package com.condabu.paymentservice.mpesa.dto;

import lombok.Data;

@Data
public class MpesaCallBackResponse {
        private String MerchantRequestID;
        private String CheckoutRequestID;
        private String ResponseCode;
        private String ResponseDescription;
        private String CustomerMessage;
}
