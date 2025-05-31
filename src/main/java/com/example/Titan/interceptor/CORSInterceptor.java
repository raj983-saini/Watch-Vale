package com.example.Titan.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class CORSInterceptor implements HandlerInterceptor {
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    log.info("toke: {}",request.getHeader("token"));
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Token, token, sec-ch-ua-platform, sec-ch-ua, sec-ch-ua-mobile, Referer, User-Agent");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Cross-Origin-Opener-Policy", "unsafe-none");
    response.setHeader("Access-Control-Max-Age", "3600"); // Cache preflight response for 1 hour

    return true;
  }
}
