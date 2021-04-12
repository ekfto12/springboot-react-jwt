package com.jy.react.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jy.react.model.dto.TokenRequestDto;
import com.jy.react.model.dto.UserLoginRequestDto;
import com.jy.react.model.dto.UserLoginResponseDto;
import com.jy.react.model.dto.UserRegisterRequestDto;
import com.jy.react.model.dto.UserRegisterResponseDto;
import com.jy.react.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<UserRegisterResponseDto> signup(@RequestBody UserRegisterRequestDto dto) {
		return ResponseEntity.ok(authService.registerUser(dto));
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto dto) {
		return ResponseEntity.ok(authService.loginUser(dto));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<UserLoginResponseDto> refresh(@RequestBody TokenRequestDto dto){
		return ResponseEntity.ok(authService.refresh(dto));
	}
}
