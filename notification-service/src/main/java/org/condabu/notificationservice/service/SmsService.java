package org.condabu.notificationservice.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.studio.v1.flow.Execution;
import com.twilio.type.PhoneNumber;
import lombok.Setter;
import org.condabu.notificationservice.common.SmsType;
import org.condabu.notificationservice.entity.SMS;
import org.condabu.notificationservice.repository.SMSRepository;
import org.springframework.beans.Mergeable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class SmsService {
    @Autowired
    private  SMSRepository smsRepository;

    @Value("${twilio.fromPhoneNumber}")
    private String fromPhoneNumber;

    public String generateSMS(SmsType smsType, Map<String, String> smsContent){
        Optional<SMS> smsTemp = smsRepository.findByType(smsType.name());

        if (smsTemp.isPresent()){
            String content = smsTemp.get().getTemplate();

            for(Map.Entry<String, String> entry : smsContent.entrySet()){
                content = content.replace("${" + entry.getKey() + "}", entry.getValue());
            }
            return content;
        }else {
            throw new IllegalArgumentException("Template not configured:"+ smsType);
        }
    }

    public String sendSms(String toPhoneNumber, String messageBody){
        try{
            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(fromPhoneNumber),
                    messageBody
            ).create();

            return "Message sent with SID: "+ message.getSid();
        }catch (Exception e){
            return "Failed to send SMS "+e.getMessage();
        }
    }

    public  String addNewSmsTemplate(SMS temp){
        smsRepository.save(temp);
        return "success";
    }
}
