package com.example.Titan.externalservice.processor;

import com.example.Titan.entity.user.UserAuth;
import org.springframework.stereotype.Service;

@Service
public class GoogleProcessor extends AbstractAuthProcessor {
    @Override
    public String getIntent(UserAuth userAuth, String appCode) throws Exception {
        return null;
    }

    @Override
    public void getUserDetail() {

    }

    @Override
    public void sendMagicLink() {

    }
}
