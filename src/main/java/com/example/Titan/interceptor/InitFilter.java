package com.example.Titan.interceptor;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@Component
@Slf4j
public class InitFilter implements Filter {
    public static final String REQUEST_ID_HEADER_NAME = "X-Request-ID";
    public static final String CORRELATION_ID_HEADER_NAME = "X-Correlation-ID";
    private static final String IP_HEADER = "clientIp";

    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        MDC.put(REQUEST_ID_HEADER_NAME, ((HttpServletRequest) request).getHeader("X-Request-ID"));

        String clientIp = getClientIp ((HttpServletRequest) request);
        log.info("ClientIPPP:{}",clientIp);
        MDC.put(IP_HEADER,clientIp);
        request.setAttribute(IP_HEADER, clientIp);
        long startTime = System.currentTimeMillis();
        log.info("Request Token: {}", ((HttpServletRequest) request).getHeader("token"));
        if (((HttpServletRequest) request).getContentType() != null
                && ((HttpServletRequest) request).getContentType().startsWith("multipart/")) {
            request = new StandardMultipartHttpServletRequest((HttpServletRequest) request);
        }
        InterceptorRequestWrapper interceptorRequestWrapper = new InterceptorRequestWrapper((HttpServletRequest) request);
        try {
            chain.doFilter(interceptorRequestWrapper, response);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Cross-Origin-Opener-Policy", "unsafe-none");
        } finally {
            String xRequestId = MDC.get(REQUEST_ID_HEADER_NAME);
            MDC.clear();
            log.info("\033[0;36m{} \033[0mTime taken by API: {} = {}ms", xRequestId, interceptorRequestWrapper.getRequestURI(), (System.currentTimeMillis() - startTime));
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    @Override
    public void destroy() {
        //destroy do nothing
    }
}

