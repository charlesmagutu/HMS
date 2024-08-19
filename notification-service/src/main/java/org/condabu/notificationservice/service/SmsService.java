package org.condabu.notificationservice.service;

import lombok.Setter;
import org.condabu.notificationservice.common.SmsType;
import org.condabu.notificationservice.entity.SMS;
import org.condabu.notificationservice.repository.SMSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class SmsService {
    @Autowired
    private  SMSRepository smsRepository;

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

    public  String addNewSmsTemplate(SMS temp){
        smsRepository.save(temp);
        return "success";
    }
}
