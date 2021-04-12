package com.jy.react.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
	
	@Id
	 @Column(name= "keyy", length = 100, nullable = false, unique = true)
	private String key;
	
	private String value;
	
	public RefreshToken updateValue(String token) {
		this.value = token;
		return this;
	}
	
	public static RefreshToken createRefreshToken(String key, String value) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.key = key;
		refreshToken.value = value;
		return refreshToken;
    }
	
	
	
}
