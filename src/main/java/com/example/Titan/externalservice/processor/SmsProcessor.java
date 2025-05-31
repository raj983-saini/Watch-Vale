package com.example.Titan.externalservice.processor;

import com.example.Titan.Response.twilio.ProviderResponseDto;
import com.example.Titan.daos.auth.UserAuthDao;
import com.example.Titan.entity.user.UserAuth;
import com.example.Titan.utils.RestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.example.Titan.constant.LoginStatusEnum.PENDING;

@Service
@Slf4j
public class SmsProcessor  extends AbstractAuthProcessor{

    @Value("${twilio.sid}")
    private String accountSid;

    @Value("${twilio.token}")
    private String authToken;

    @Value("${twilio.number}")
    private String twilioPhoneNumber;

    private final RestUtils restUtils;
    private final ObjectMapper objectMapper;
    private final UserAuthDao userAuthDao;

    public SmsProcessor(RestUtils restUtils, ObjectMapper objectMapper, UserAuthDao userAuthDao) {
        this.restUtils = restUtils;
        this.objectMapper = objectMapper;
        this.userAuthDao = userAuthDao;
    }

    public ProviderResponseDto sendSms(UserAuth userAuth, String appCode) {
        ProviderResponseDto responseDto = new ProviderResponseDto();

        try {
            String msg = "<#> " + userAuth.getInternalToken() +
                    " is your Self sign-in OTP. Don't share it with anyone. " +
                    (appCode != null ? appCode : "");

            // Initialize Twilio
            Twilio.init(accountSid, authToken);

            // Send SMS
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(userAuth.getMobile()),
                    new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                    msg
            ).create();
            userAuth.setStatus(PENDING.name());
            // Log Twilio response
            log.info("Twilio Message SID: {}", message.getSid());

            // Prepare success response
            responseDto.setStatus("Success");
            responseDto.setMessage("OTP sent successfully");
            responseDto.setProviderMessageId(message.getSid());
        } catch (Exception e) {
            log.error("Error sending SMS via Twilio: ", e);

            // Prepare error response
            responseDto.setStatus("Failed");
            responseDto.setMessage("Failed to send OTP: " + e.getMessage());
        }
        userAuthDao.save(userAuth);
        return responseDto;
    }

    @Override
    public String getIntent(UserAuth userAuth, String appCode) throws Exception {
        ProviderResponseDto responseDto1 = sendSms(userAuth, appCode);
        if(responseDto1.getMessage().equals("Success")){
            return userAuth.getCode();
        }
        return  responseDto1.getMessage();
        }



    @Override
    public void getUserDetail() {

    }

    @Override
    public void sendMagicLink() {

    }
}
