package com.example.web.Security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUtils {

    public static List<String> getRoles(Authentication authentication){
        if(authentication == null){
            return new ArrayList<>();
        }
        return authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
    public static Long userId(Authentication authentication){
        if(authentication == null){
            return null;
        }
        return Long.valueOf(authentication.getPrincipal().toString());
    }
}
