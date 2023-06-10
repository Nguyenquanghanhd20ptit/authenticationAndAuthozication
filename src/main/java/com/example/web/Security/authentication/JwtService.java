package com.example.web.Security.authentication;

import com.example.web.data.pojo.ShortJwtPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.web.data.constant.JwtConstant.USER_INFO;

@Service
public class JwtService {

    @Autowired
    JwtConfig jwtConfig;


    public AbstractAuthenticationToken extractToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
            String userString = (String) claims.get(USER_INFO);
            ShortJwtPayload shortJwtPayload = new ObjectMapper().readValue(userString, ShortJwtPayload.class);
            return new UsernamePasswordAuthenticationToken(shortJwtPayload.getUserId(), shortJwtPayload, getAuthorities(shortJwtPayload.getRoles()));

        } catch (ExpiredJwtException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public List<SimpleGrantedAuthority> getAuthorities(List<String> roles) {
        return Optional.ofNullable(roles).orElse(new ArrayList<>())
                .stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public String gennerateJwt(ShortJwtPayload shortJwtPayload) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put(USER_INFO, new ObjectMapper().writeValueAsString(shortJwtPayload));

        return Jwts.builder()
                .setSubject(String.valueOf(shortJwtPayload.getUserId()))
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpires()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
                .compact();
    }
}
