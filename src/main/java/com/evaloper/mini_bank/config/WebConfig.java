package com.evaloper.mini_bank.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

private final ApiKeyInterceptor apiKeyInterceptor;

//    public WebConfig(ApiKeyInterceptor apiKeyInterceptor) {
//        this.apiKeyInterceptor = apiKeyInterceptor;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyInterceptor).addPathPatterns("/api/v1/user/**");
    }
}
