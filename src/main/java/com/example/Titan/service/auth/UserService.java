package com.example.Titan.service.auth;

import com.example.Titan.constant.ErrorMessages;
import com.example.Titan.daos.auth.UserAuthDao;
import com.example.Titan.daos.auth.UserDao;
import com.example.Titan.daos.auth.UserTokensDao;
import com.example.Titan.dtos.auth.UserAuthCreate;
import com.example.Titan.dtos.auth.UserCreateDto;
import com.example.Titan.dtos.auth.VerifyOtpDto;
import com.example.Titan.entity.user.User;
import com.example.Titan.entity.user.UserAuth;
import com.example.Titan.entity.user.UserToken;
import com.example.Titan.expections.CustomException;
import com.example.Titan.externalservice.ProcessorFactory;
import com.example.Titan.externalservice.wishlist.WishlistNumber;
import com.example.Titan.utils.JwtUtils;
import com.example.Titan.utils.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.example.Titan.constant.LoginStatusEnum.INTIATE;
import static com.example.Titan.constant.LoginStatusEnum.PENDING;
import static java.util.Objects.nonNull;

@Service
public class UserService {
    private  final UserDao userDao;
    private  final UserAuthDao userAuthDao;
    private  final JwtUtils jwtUtils;
    private  final UserTokensDao userTokenDao;
    private final ProcessorFactory processorFactory;

    public UserService(UserDao userDao, UserAuthDao userAuthDao, JwtUtils jwtUtils, UserTokensDao userTokenDao, ProcessorFactory processorFactory) {
        this.userDao = userDao;
        this.userAuthDao = userAuthDao;
        this.jwtUtils = jwtUtils;
        this.userTokenDao = userTokenDao;
        this.processorFactory = processorFactory;
    }
    public  String getIntent(UserAuthCreate createDto) throws Exception {
        if (nonNull(createDto.getMobile())){
            UserAuth userAuth = new UserAuth();
            String internalToken = StringUtil.getKey(12);
            userAuth.setStatus(INTIATE.name());
            userAuth.setChannel(String.valueOf(createDto.getChannel()));
            userAuth.setCode(internalToken);
            userAuth.setExpiredAt(LocalDateTime.now().plusMinutes(5L));
            if (createDto.getMobile() != null){
                userAuth.setMobile(createDto.getMobile());
            }
            userAuthDao.save(userAuth);
            if (WishlistNumber.WISHLIST_NUMBER.contains(createDto.getMobile())){
               userAuth.setInternalToken("1234");
               userAuth.setStatus(PENDING.name());
                userAuthDao.save(userAuth);
                return userAuth.getCode();
            }else {
                String otp = StringUtil.generateOTP();
                userAuth.setInternalToken(otp);}
            userAuthDao.save(userAuth);

            // AbstractAuthProcessor processor = processorFactory.getProcessor(createDto.getChannel());
         //   return processor.getIntent(userAuth,otp);
        }
        return null;
    }
    public  Map<String, Object> verifyOtp(VerifyOtpDto verifyOtpDto) throws CustomException {
        Map<String, Object>  response = new HashMap<>();
        UserAuth userAuth = validateUser(verifyOtpDto);
        User user = ensureUserExit(verifyOtpDto.getMobile());
        String accessToken = JwtUtils.genrateToken(user, 1000L * 60 * 60 * 24 * 30);
        String refreshToken = JwtUtils.generateRefreshToken(user, 1000L * 60 * 60 * 24 * 100);

        UserToken userTokens = new UserToken();
        userTokens.setUserId(user.getId());
        userTokens.setAccessToken(accessToken);
        userTokens.setRefreshToken(refreshToken);
        userTokens.setStatus("VERIFIED");
        userTokens.setAccessTokenExpiration(LocalDateTime.now().plusDays(30));
        userTokens.setRefreshTokenExpiration(LocalDateTime.now().plusDays(100));

        userTokenDao.save(userTokens);

        response.put("AccessToken " , userTokens.getAccessToken());
        response.put("RsfershToken",userTokens.getRefreshToken());
        response.put("Status",userTokens.getStatus());
        return response;
    }

    public UserAuth validateUser(VerifyOtpDto verifyOtpDto) throws CustomException {
        UserAuth userAuth = userAuthDao.findByCodeOrderByCreatedAtDesc(verifyOtpDto.getState())
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST
                        , ErrorMessages.SOMETHING_WENT_WRONG, "user not found"));
        if (!userAuth.getInternalToken().equals(verifyOtpDto.getOtp())){
            throw  new CustomException(HttpStatus.BAD_REQUEST , ErrorMessages.SOMETHING_WENT_WRONG,"wrong otp sent");
        }
        if (!LocalDateTime.now().isBefore(userAuth.getExpiredAt())){
            throw  new CustomException(HttpStatus.BAD_REQUEST , ErrorMessages.CODE_EXPIRED," otp expired please resend");
        }
        if (userAuth == null || !PENDING.name().equals(userAuth.getStatus())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "we are experiencing technical issues, try again");
        }
        return userAuth;
    }

public User ensureUserExit(String mobile){
    User user = userDao.findByMobileAndIsDeletedNotTrue(mobile);
    if(user == null ){
        var pastdeleteduser = userDao.findByMobileAndIsDeletedTrue(mobile);
       user = new User();
       user.setMobile(mobile);
       user.setUid(UUID.randomUUID().toString().replace("-",".").toUpperCase());
       userDao.save(user);

    }
    return user;
}
    public Optional<User> getUserByUserId(Long userId) {
        Optional<User> user = userDao.findById(userId);
        System.out.println(user);
        return user;
    }

    public UserCreateDto getUser(Long id){
        Optional<User> byId = userDao.findById(id);
        User user = byId.get();
        UserCreateDto userDetails = new UserCreateDto();
        userDetails.setEmail(user.getEmail());
        userDetails.setDob(user.getDob());
        userDetails.setName(user.getName());
        userDetails.setProfileUrl(user.getProfileUrl());
        userDetails.setMobile(user.getMobile());
        return userDetails;
    }
    public String updateUser(UserCreateDto userDetails, Long id){
        Optional<User> byId = userDao.findById(id);
        User user = byId.get();
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        user.setDob(userDetails.getDob());
        user.setMobile(userDetails.getMobile());
        user.setProfileUrl(userDetails.getProfileUrl());
        userDao.saveAndFlush(user);
        return "user details update successfully";
    }
}

