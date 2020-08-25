package com.cos.instargram.web.dto;

import com.cos.instargram.domain.user.User;
import com.cos.instargram.domain.user.UserRole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinReqDto {
	private String email;
	private String name;
	private String username;
	private String password;

	public User toEntity() {
		return User.builder()
				.email(email)
				.name(name)
				.username(username)
				.password(password)
				.role(UserRole.USER)
				.build();
	}

}
