package com.cos.instargram.domain.user;

import lombok.Getter;

@Getter
public enum UserRole {
	USER("ROLE_USER","일반 유저"),	ADMIN("ROLE_ADMIN");
	
	UserRole(String key){
		this.key=key;
	}
	
	private String key;

}
