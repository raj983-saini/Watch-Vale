package com.example.Titan.externalservice.processor;


import com.example.Titan.entity.user.UserAuth;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractAuthProcessor {

    public abstract String getIntent(UserAuth userAuth, String appCode) throws Exception;
//    public abstract String getIntent(DeletedUser deletedUser, String appCode) throws Exception;

    public abstract void getUserDetail();

    public abstract void sendMagicLink();

//    public abstract void handleCallback(CallBackDto callBackDto) throws CustomException;
}
