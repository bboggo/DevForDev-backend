package com.backend.devfordev.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "jwt")

public class JwtProperties {

    private String issuer;
    private String secretKey;
    private long expirationMinutes;
    private long refreshExpirationDays;
    private long reissueLimit;
}