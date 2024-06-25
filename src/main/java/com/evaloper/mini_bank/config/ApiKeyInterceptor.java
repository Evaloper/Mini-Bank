package com.evaloper.mini_bank.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    private final List<String> allowedApiKeys;

    public ApiKeyInterceptor(List<String> allowedApiKeys) {
        this.allowedApiKeys = allowedApiKeys;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String apiKey = request.getHeader("API-Key");
        if (apiKey == null || !allowedApiKeys.contains(apiKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }
}
