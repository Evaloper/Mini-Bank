package com.evaloper.mini_bank.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@NoArgsConstructor
public class AirtimeConfig {
    @Value("${tm30-test-recharge.url}")
    private String testRechargeUrl;
    @Value("${recharge.test-key}")
    private String rechargeTestKey;
}
