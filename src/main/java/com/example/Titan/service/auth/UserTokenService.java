package com.example.Titan.service.auth;


import com.example.Titan.daos.auth.UserTokensDao;
import com.example.Titan.entity.user.UserToken;
import com.example.Titan.expections.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserTokenService {

    private final UserTokensDao userTokensDao;


    public UserTokenService(UserTokensDao userTokensDao) {
        this.userTokensDao = userTokensDao;
    }

    public UserToken checkToken(String token) throws CustomException {
        Optional<UserToken> userTokensOptional = userTokensDao.findFirstByAccessToken(token);
        if (userTokensOptional.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "token not present!");
        }
        UserToken userTokens = userTokensOptional.get();

        if (LocalDateTime.now().isAfter(userTokens.getAccessTokenExpiration())){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Something went wrong !");
        }
        return userTokens;
    }

    public UserToken saveToken(UserToken userTokens) {
        return userTokensDao.save(userTokens);
    }

    public UserToken getRefreshToken(String refreshToken) throws CustomException {
        Optional<UserToken> userTokensOptional = userTokensDao.findByRefreshTokenAndStatus(refreshToken,"VERIFIED");
        if (userTokensOptional.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "token not present!");
        }
        UserToken userTokens = userTokensOptional.get();

        if (LocalDateTime.now().isAfter(userTokens.getRefreshTokenExpiration())){
            throw new CustomException(HttpStatus.BAD_REQUEST, "refresh token expired!");
        }
        return userTokens;
    }



}
