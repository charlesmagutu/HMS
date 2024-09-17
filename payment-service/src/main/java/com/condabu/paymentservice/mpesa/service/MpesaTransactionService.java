package com.condabu.paymentservice.mpesa.service;

import com.condabu.paymentservice.mpesa.model.MpesaTransaction;
import com.condabu.paymentservice.mpesa.repository.MpesaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MpesaTransactionService {
    @Autowired
    private MpesaRepository mpesaRepository;
    public MpesaTransaction saveMpesaTransaction(MpesaTransaction mpesaTransaction){
        return mpesaRepository.save(mpesaTransaction);
    }


    public ResponseEntity<String> handleCallback(Map<String, Object> callbackData) {
        try {
            log.info("Received callback from M-pesa: {}", callbackData);
            Map<String, Object> body = (Map<String, Object>) callbackData.get("Body");
            Map<String, Object> stkCallback = (Map<String, Object>) body.get("stkCallback");

            String merchantRequestID = (String) stkCallback.get("MerchantRequestID");
            String checkoutRequestID = (String) stkCallback.get("CheckoutRequestID");
            int resultCode = Integer.parseInt(stkCallback.get("ResultCode").toString());
            String resultDesc = (String) stkCallback.get("ResultDesc");

            Map<String, Object> callbackMetadata = (Map<String, Object>) stkCallback.get("CallbackMetadata");
            List<Map<String, Object>> transactionData = (List<Map<String, Object>>) callbackMetadata.get("Item");

            double amount = 0.0;
            String mpesaReceiptNumber = null;
            LocalDateTime transactionDate = null;
            String phoneNumber = null;

            for (Map<String, Object> item : transactionData) {
                String name = (String) item.get("Name");
                Object value = item.get("Value");

                switch (name) {
                    case "Amount":
                        amount = Double.parseDouble(value.toString());
                        break;
                    case "MpesaReceiptNumber":
                        mpesaReceiptNumber = value.toString();
                        break;
                    case "PhoneNumber":
                        phoneNumber = value.toString();
                        break;
                    case "TransactionDate":
                        transactionDate = transactionDateTime(value.toString());
                        break;
                }
            }

            MpesaTransaction mpesaTransaction = new MpesaTransaction();
            mpesaTransaction.setMerchantRequestID(merchantRequestID);
            mpesaTransaction.setCheckoutRequestID(checkoutRequestID);
            mpesaTransaction.setResultCode(resultCode);
            mpesaTransaction.setResultDesc(resultDesc);
            mpesaTransaction.setAmount(amount);
            mpesaTransaction.setMpesaReceiptNumber(mpesaReceiptNumber);
            mpesaTransaction.setTransactionDate(transactionDate);
            mpesaTransaction.setPhoneNumber(phoneNumber);

            mpesaRepository.save(mpesaTransaction);

            log.info("M-Pesa transaction saved successfully: {}", mpesaTransaction);
            return new ResponseEntity<>("Callback processed successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error processing M-Pesa callback: ", e);
            return new ResponseEntity<>("Error processing callback", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private LocalDateTime transactionDateTime(String date) {
        if (date.length() == 14) {
            return LocalDateTime.of(
                    Integer.parseInt(date.substring(0, 4)),
                    Integer.parseInt(date.substring(4, 6)),
                    Integer.parseInt(date.substring(6, 8)),
                    Integer.parseInt(date.substring(8, 10)),
                    Integer.parseInt(date.substring(10, 12)),
                    Integer.parseInt(date.substring(12, 14))
            );
        }
        return LocalDateTime.now();
    }
}
