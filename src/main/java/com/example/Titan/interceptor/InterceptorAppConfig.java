package com.example.Titan.interceptor;

import com.example.Titan.constant.AccessList;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorAppConfig implements WebMvcConfigurer {

  private final CORSInterceptor corsInterceptor;
  private final ValidateTokenInterceptor validateTokenInterceptor;
  private final Validateb2bTokenInterceptor validateb2bTokenInterceptor;

  public InterceptorAppConfig(CORSInterceptor corsInterceptor, ValidateTokenInterceptor validateTokenInterceptor, Validateb2bTokenInterceptor validateb2bTokenInterceptor) {
    this.corsInterceptor = corsInterceptor;
    this.validateTokenInterceptor = validateTokenInterceptor;
      this.validateb2bTokenInterceptor = validateb2bTokenInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(corsInterceptor);
    registry.addInterceptor(validateTokenInterceptor)
            .addPathPatterns(AccessList.USER_TOKEN_INCLUDE_ENDPOINTS)
            .excludePathPatterns(AccessList.USER_TOKEN_EXCLUDE_ENDPOINTS);
    registry.addInterceptor(validateb2bTokenInterceptor)
            .addPathPatterns(AccessList.B2B_TOKEN_INCLUDE_ENDPOINTS)
            .excludePathPatterns(AccessList.B2B_TOKEN_EXCLUDE_ENDPOINTS);
  }
}