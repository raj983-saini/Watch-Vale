package com.example.Titan.constant;

import java.util.List;

public class AccessList {
    public static final List<String> USER_TOKEN_EXCLUDE_ENDPOINTS = List.of("/t1/auth/**", "/healthCheck","/t1/watch/all",
            "/v1/upi/callback", "/v1/external/**", "/b2b/**");
    public static final List<String> USER_TOKEN_INCLUDE_ENDPOINTS = List.of("/t1/**");
    public static final List<String> B2B_TOKEN_EXCLUDE_ENDPOINTS = List.of("/b2b/t1/auth/**","/b2b/t1/callback/**" );
    public static final List<String> B2B_TOKEN_INCLUDE_ENDPOINTS = List.of("/b2b/t1/**");

}
