package org.condabu.notificationservice.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SmsTypeConverter implements Converter<String, SmsType> {
    @Override
    public SmsType convert(String source){
        try{
            return SmsType.valueOf(source.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid SMS type:" + source , e);
        }
    }

}
