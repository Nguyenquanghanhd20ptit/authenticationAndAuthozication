package com.example.web.Security.authentication;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "spring.security.jwt")
public class JwtConfig {
    private String secretKey ;
    private Long expires;
}
