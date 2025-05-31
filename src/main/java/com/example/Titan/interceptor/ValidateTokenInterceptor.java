package com.example.Titan.interceptor;

import com.example.Titan.Response.ApiResponseDto;
import com.example.Titan.entity.user.Role;
import com.example.Titan.entity.user.User;
import com.example.Titan.entity.user.UserToken;
import com.example.Titan.service.auth.RoleService;
import com.example.Titan.service.auth.UserService;
import com.example.Titan.service.auth.UserTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Optional;

import static com.example.Titan.constant.ErrorConstant.UNAUTHORIZED;

@Slf4j
@Component
public class ValidateTokenInterceptor implements HandlerInterceptor {

  private final UserTokenService userTokenService;
  private final UserService userService;
  private final RoleService roleService;

  public ValidateTokenInterceptor(UserTokenService userTokenService, UserService userService, RoleService roleService) {
    this.userTokenService = userTokenService;
    this.userService = userService;
      this.roleService = roleService;
  }

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler) throws Exception {
    log.info("Pre handle Interceptor of ValidateTokenInterceptor for token {}", request.getHeader("token"));
    String token = request.getHeader("token");

    try {
      if (ObjectUtils.isEmpty(token)) {
        InterceptorRequestWrapper interceptorRequestWrapper = new InterceptorRequestWrapper(request);
        log.error("Token Value is Blank or Empty for request {}", interceptorRequestWrapper.getRequestURI());
        sendFailureResponse(response, UNAUTHORIZED);
        return false;
      } else {
        log.info("token found: {}", token);
        try {
          UserToken userTokens = userTokenService.checkToken(token);
          System.out.println("userId" + userTokens.getUserId());
          Optional<User> users = userService.getUserByUserId(userTokens.getUserId());
          System.out.println("USERS GOT: " + users);
          if(users.isEmpty()){
            log.info("user not found error for {}", token);
            sendFailureResponse(response, UNAUTHORIZED);
            return false;
          }
          if(users.get().getRole() == null){
            request.setAttribute("userTokens",userTokens);
            request.setAttribute("user", users.get());
          }else{
            Optional<Role> role = roleService.getRoleById(users.get().getRole());
            if(role.isEmpty()){
              log.info("role not found in {} error for {}", users, token);
              sendFailureResponse(response, UNAUTHORIZED);
              return false;
            }
            if(role.get().isAllowOtpLogin()){
              System.out.println("okkkkk"+role.get().isAllowOtpLogin());
              request.setAttribute("userTokens",userTokens);
              request.setAttribute("user", users.get());
            }else{
              log.info("otp login is not allowed in {} error for {}", role, users);
              sendFailureResponse(response, UNAUTHORIZED);
              return false;
            }
          }
        } catch (Exception ue) {
          log.info("{} error for {}", ue, token);
          sendFailureResponse(response, UNAUTHORIZED);
          return false;
        }
        return true;
      }
    } catch (Throwable th) {
      log.error("Exception occurred in Pre handle Interceptor ValidateTokenInterceptor {}", th);
    }
    sendFailureResponse(response, UNAUTHORIZED);
    return false;
  }


  private void sendFailureResponse(HttpServletResponse response, String responseCode) throws IOException {
    response.setStatus(Integer.parseInt(responseCode));
    ApiResponseDto responseDto = new ApiResponseDto();
    responseDto.setStatusCode(401);
    ObjectMapper mapper = new ObjectMapper();
    response.getWriter().write(mapper.writeValueAsString(responseDto));
    response.flushBuffer();
  }
}
