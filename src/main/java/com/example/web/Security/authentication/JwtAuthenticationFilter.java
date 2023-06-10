package com.example.web.Security.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        AbstractAuthenticationToken authenticationToken;
        HttpSession session = request.getSession();
        String header = (String) session.getAttribute("Authorization");
        String v= header;
        try {
            if (header != null && header.startsWith("Bearer ")) {
                // giai ma token
                authenticationToken = jwtService.extractToken(header.substring(7));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request,response);
        }
    }
}
