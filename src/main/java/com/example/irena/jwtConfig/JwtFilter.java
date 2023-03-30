package com.example.irena.jwtConfig;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
           if (requestHeader == null || !requestHeader.startsWith("Bearer")) {
               filterChain.doFilter(request, response);
               return;
           }
           String token = requestHeader.replace("Bearer ", "");
           Claims claims = JwtGenerate.isValidAccessToken(token);
           if (claims == null) {
               filterChain.doFilter(request, response);
               return;
           }
           List<LinkedHashMap<String, String>> authorities = JwtGenerate.getAuthorities(claims);
           Authentication authentication = new UsernamePasswordAuthenticationToken(
                   claims.getSubject(), null,
                   getAuthorities(authorities)
           );
           SecurityContextHolder.getContext().setAuthentication(authentication);
           filterChain.doFilter(request, response);

    }

    private List<SimpleGrantedAuthority> getAuthorities(List<LinkedHashMap<String, String>> authorities) {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorities.forEach((map) -> {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                authorityList.add(new SimpleGrantedAuthority(entry.getValue()));
            }
        });
        return authorityList;
    }
}
