package com.jy.react.model.dto;




import com.jy.react.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {

	private String uid;
	private String password;

}
