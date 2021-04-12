package com.jy.react.model.dto;

import com.jy.react.domain.RefreshToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDto {
	private String id;
	private String token;
	private String refreshToken;
}
