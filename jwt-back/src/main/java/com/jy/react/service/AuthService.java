package com.jy.react.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.react.config.JwtTokenProvider;
import com.jy.react.domain.RefreshToken;
import com.jy.react.domain.User;
import com.jy.react.model.dto.TokenRequestDto;
import com.jy.react.model.dto.UserLoginRequestDto;
import com.jy.react.model.dto.UserLoginResponseDto;
import com.jy.react.model.dto.UserRegisterRequestDto;
import com.jy.react.model.dto.UserRegisterResponseDto;
import com.jy.react.repository.RefreshTokenRepository;
import com.jy.react.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	@Transactional
	public UserRegisterResponseDto registerUser(UserRegisterRequestDto dto) {
		if(userRepository.existsByUid(dto.getUid())) {
			throw new RuntimeException("이미 가입되어있는 아이디입니다.");
		}
		User user = userRepository.save(
				User.createUser(
						dto.getUid(),
						passwordEncoder.encode(dto.getPassword())
						));
		return new UserRegisterResponseDto(user.getId(), user.getUid());
	}
	
	@Transactional
	public UserLoginResponseDto loginUser(UserLoginRequestDto dto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUid(), dto.getPassword());
		
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		String token = jwtTokenProvider.createToken(authentication);
		String refresh = jwtTokenProvider.createRefreshToekn();
		
		RefreshToken refreshToken = refreshTokenRepository.save(
									RefreshToken.createRefreshToken(
											authentication.getName(),
											refresh
											));
		
		refreshTokenRepository.save(refreshToken);
		
		return new UserLoginResponseDto(authentication.getName(), token, refresh);
	}
	
	@Transactional
	public UserLoginResponseDto refresh(TokenRequestDto dto) {
		if(!jwtTokenProvider.validateToken(dto.getRefresh())) {
			throw new RuntimeException("Refresh Token 이 유효하지 않습니다");
		}
		
		Authentication authentication = jwtTokenProvider.getAuthentication(dto.getToken());
		
		RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
				.orElseThrow(() -> new RuntimeException("로그아웃 된 사용자 입니다."));
		
		if(!refreshToken.getValue().equals(dto.getRefresh())) {
			throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다");
		}
		
		String token = jwtTokenProvider.createToken(authentication);
		String refresh = jwtTokenProvider.createRefreshToekn();
		
		RefreshToken newRefreshToken = refreshToken.updateValue(refresh);
		refreshTokenRepository.save(newRefreshToken);
		
		return new UserLoginResponseDto(authentication.getName(), token, refresh);
		
	}
	
}
