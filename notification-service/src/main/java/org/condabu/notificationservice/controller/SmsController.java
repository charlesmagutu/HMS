package org.condabu.notificationservice.controller;

import org.condabu.notificationservice.common.SmsType;
import org.condabu.notificationservice.entity.SMS;
import org.condabu.notificationservice.repository.SMSRepository;
import org.condabu.notificationservice.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notification/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @PostMapping
    public String addNewSMSTemplate(@RequestBody SMS sms){
        return smsService.addNewSmsTemplate(sms);
    }

    @PostMapping("/send")
    public String sendSmsNotification(@RequestParam SmsType smsType, @RequestBody Map<String, String> info){
        try{

            return smsService.sendSms("",smsService.generateSMS(smsType, info));

        }catch (IllegalArgumentException e){
            return "Error: "+e.getMessage();
        }

    }
}
