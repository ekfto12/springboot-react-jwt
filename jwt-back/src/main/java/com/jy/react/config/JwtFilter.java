package com.jy.react.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter  extends OncePerRequestFilter {
	
	private final JwtTokenProvider jwtTokenProvider;
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    	
    	String jwt = resolveToken(request);
    	
    	if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
    		Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
    		SecurityContextHolder.getContext().setAuthentication(authentication);
    	}
    	filterChain.doFilter(request, response);
    }
    
    private String resolveToken(HttpServletRequest request) {
    	String beareToken = request.getHeader(AUTHORIZATION_HEADER);
    	if(StringUtils.hasText(beareToken) && beareToken.startsWith(BEARER_PREFIX)) {
    		return beareToken.substring(7);
    	}
    	return null;
    }
	
}
