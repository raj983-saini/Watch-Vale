package com.example.Titan.externalservice;

import com.example.Titan.constant.ChannelEnum;
import com.example.Titan.externalservice.processor.AbstractAuthProcessor;
import com.example.Titan.externalservice.processor.GoogleProcessor;
import com.example.Titan.externalservice.processor.SmsProcessor;
import com.example.Titan.externalservice.processor.WhatsAppProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProcessorFactory {

    private final GoogleProcessor googleProcessor;

    private final WhatsAppProcessor whatsAppProcessor;
    private final SmsProcessor smsProcessor;

    public ProcessorFactory(GoogleProcessor googleProcessor, WhatsAppProcessor whatsAppProcessor, SmsProcessor smsProcessor) {
        this.googleProcessor = googleProcessor;
        this.whatsAppProcessor = whatsAppProcessor;
        this.smsProcessor = smsProcessor;
    }

    public AbstractAuthProcessor getProcessor(ChannelEnum channel) {
        switch (channel) {
            case GOOGLE:
                return googleProcessor;
            case WHATSAPP:
                return whatsAppProcessor;
            case SMS:
                return smsProcessor;
            default:
                log.error("Failed to return auth processor object while processing for channel: {}", channel);
                return null;
        }
    }
}
