package com.brevitylink.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqids.Sqids;

@Configuration
public class SqidsConfig {
    @Value("${ALPHABET_SECRET}")
    private String secret;
    @Bean
    public Sqids sqids() {
        return Sqids.builder()
                .minLength(7)
                .alphabet(secret)
                .build();
    }
}

