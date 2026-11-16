package com.ssheavyhaulage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                // Nominatim requires a valid custom User-Agent header per usage policy
                .defaultHeader("User-Agent", "SSHeavyHaulagePortal/1.0 (contact@ssheavyhaulage.co.za)")
                .build();
    }
}