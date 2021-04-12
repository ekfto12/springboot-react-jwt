package com.jy.react.config;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.jy.react.service.CustomUserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component

public class JwtTokenProvider {
	
	
	
	@Value("${jwt.secret}")
	private String secret;
	
	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_TYPE = "bearer";
	private long tokenValidTime = 60 * 60 * 1000L; // 1시간
    private long refreshTokenValidMillisecond = 1000L * 60 * 60 * 24 * 7; // 7일
    
    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
    	byte[] keyButes = Decoders.BASE64.decode(secretKey);
    	this.key = Keys.hmacShaKeyFor(keyButes);
    }
    
    public String createToken(Authentication authentication) {
    	String authorities = authentication.getAuthorities().stream()
    			.map(GrantedAuthority::getAuthority)
    			.collect(Collectors.joining(","));
    	
    	long now = (new Date()).getTime();
    	Date validity = new Date(now + this.tokenValidTime);
    	
    	return Jwts.builder()
    			.setSubject(authentication.getName())
    			.claim(AUTHORITIES_KEY, authorities)
    			.setExpiration(validity)
    			.signWith(key, SignatureAlgorithm.HS512)
    			.compact();

    }
    
    public String createRefreshToekn() {
    	long now = (new Date()).getTime();
    	
    	return Jwts.builder()
    			.setExpiration(new Date(now + refreshTokenValidMillisecond))
    			.signWith(key, SignatureAlgorithm.HS512)
    			.compact();
    }
    
    public Authentication getAuthentication(String token) {
    	Claims claims = parseClaims(token);
    	if(claims.get(AUTHORITIES_KEY) == null) {
    		throw new RuntimeException("권한 정보가 없는 토큰입니다.");
    	}
    	Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    	
    	UserDetails principal = new User(claims.getSubject(), "", authorities);
    	
    	return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
    
    public boolean validateToken(String token) {
    	try {
    		Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    		return true;
    	} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
    		throw new RuntimeException("잘못된 JWT 서명입니다");
    	} catch (ExpiredJwtException e) {
    		throw new RuntimeException("만료된 JWT 토큰입니다");
    	} catch (UnsupportedJwtException e) {
    		throw new RuntimeException("지원되지 않는 JWT 토큰입니다");
    	} catch (IllegalArgumentException e) {
    		throw new RuntimeException("JWT 토큰이 잘못되었습니다.");
    	}

    }
    
    private Claims parseClaims(String token) {
    	try {
    		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    	} catch (ExpiredJwtException e) {
    		return e.getClaims();
    	}
    }

}
