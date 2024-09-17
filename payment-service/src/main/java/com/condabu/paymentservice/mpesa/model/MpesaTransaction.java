package com.condabu.paymentservice.mpesa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MpesaTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String merchantRequestID;
    private String checkoutRequestID;
    private int resultCode;
    private String resultDesc;
    private double amount;
    private String mpesaReceiptNumber;
    private LocalDateTime transactionDate;
    private String phoneNumber;
}

