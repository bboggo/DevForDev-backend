package com.backend.devfordev.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)

public class TokenProvider {

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 토큰 생성 메서드
    public String createAccessToken(String userSpecification) {

        return Jwts.builder()
                .signWith(new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setSubject(userSpecification)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(jwtProperties.getExpirationMinutes(), ChronoUnit.MINUTES)))
                .compact();
    }

    // 비밀키를 토대로 createToken()에서 토큰에 담은 Subject를 복호화하여 문자열 형태로 반환하는 메소드
    public String validateTokenAndGetSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 리프레시 토큰 생성 메서드
    public String createRefreshToken() {
        return Jwts.builder()
                .signWith(new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(jwtProperties.getRefreshExpirationDays(), ChronoUnit.DAYS)))
                .compact();
    }

    // 액세스 토큰 재발급 메서드
    public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
        String subject = decodeJwtPayloadSubject(oldAccessToken);
        return createAccessToken(subject);
    }


    public void validateRefreshToken(String refreshToken, String oldAccessToken) throws JsonProcessingException {
        validateAndParseToken(refreshToken);
        decodeJwtPayloadSubject(oldAccessToken);
    }

    private Jws<Claims> validateAndParseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())
                .build()
                .parseClaimsJws(token);
    }

    private String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
                Map.class
        ).get("sub").toString();
    }
}
