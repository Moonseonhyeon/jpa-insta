package com.cos.instargram.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.instargram.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(PrincipalDetails.class);
	
	private User user;
	private Map<String, Object> attributes; //Object로 만들기 힘듬.

	//일반 로그인용 생성자
    public PrincipalDetails(User user){    	
        this.user = user;
    }
    
    //OAuth 로그인 용 생성자
    public PrincipalDetails(User user,  Map<String, Object> attributes){
    	log.info("PrincipalDetails 생성자 진입 : OAuth2User");
        this.user = user;
        this.attributes =attributes;
    }
    
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<GrantedAuthority>();		
		collection.add(()-> user.getRole().getKey());
		return collection;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getProviderId(); // 프라이머리키, 
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}


	@Override
	public Map<String, Object> getAttributes() {
		//페이스북, 구글, 값이 다 다르니까 Map으로 받음.
		//private Map<String,Object> attributes;
		
		return attributes;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return user.getProvider();
	}
	
}
