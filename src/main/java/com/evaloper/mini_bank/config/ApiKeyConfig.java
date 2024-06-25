package com.evaloper.mini_bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class ApiKeyConfig {

    @Bean
    public List<String> allowedApiKeys() {
        return Collections.singletonList("helloCash-api-key");
    }
}
