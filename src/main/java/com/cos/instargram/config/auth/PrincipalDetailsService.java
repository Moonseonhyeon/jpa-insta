package com.cos.instargram.config.auth;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.instargram.config.auth.dto.LoginUser;
import com.cos.instargram.domain.user.User;
import com.cos.instargram.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{
	
	private static final Logger log = LoggerFactory.getLogger(PrincipalDetailsService.class);
	private final UserRepository userRepository;
	private final HttpSession session;
	
	//Security Session > Authentication > UserDetails
	//해당함수가 정상적으로 리턴되면 @AuthenticationPrincipal 어노테이션 활성화됨.
	//form 로그인 할 때 이 함수가 호출됩니다
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService : 진입");
		log.info("loadUserByUsername : username : " + username);
		User userEntity = userRepository.findByUsername(username).get();
		if(userEntity != null) {
			System.out.println("유저가 있어요!!");
			session.setAttribute("loginUser", new LoginUser(userEntity));
		}
		
		return new PrincipalDetails(userEntity);
	}

}
