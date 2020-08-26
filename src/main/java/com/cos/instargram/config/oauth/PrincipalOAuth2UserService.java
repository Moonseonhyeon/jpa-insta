package com.cos.instargram.config.oauth;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.instargram.config.auth.PrincipalDetails;
import com.cos.instargram.domain.user.User;
import com.cos.instargram.domain.user.UserRepository;
import com.cos.instargram.domain.user.UserRole;

@Service
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService{
	
	private static final Logger log = LoggerFactory.getLogger(PrincipalOAuth2UserService.class);
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Value("${cos.secret}")
	private String cosSecret; //yml파일

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info(userRequest.toString()); //내 서버의 기본 정보를 들고있음. 얘가 페이스북에 요청한다. 아직 사용자 프로필 정보까지는 안받은 상태임.
		log.info(userRequest.getAccessToken().getTokenValue());
		log.info(userRequest.getClientRegistration().toString());
		
		OAuth2User oAuth2User = super.loadUser(userRequest); //페이스북으로 부터 프로필 정보를 받아서 oAuth2User에 쏙 넣음.
		System.out.println("OAuth2User : "+oAuth2User.getAttributes());
		
		User userEntity = oauthLoginOrJoin(oAuth2User);
		
		return new PrincipalDetails(null, oAuth2User.getAttributes());  //페이스북으로 접근하면 회원가입시킴
		//super.loadUser()는 OAuth 서버에 내 서버정보와 AccessToken을 던져
		//회원 프로필 정보를 OAuth2User타입으로 받아온다. Map으로 생겼다.
	}
	
	private User oauthLoginOrJoin(OAuth2User oAuth2User) {
		String provider = "facebook";
		String providerId = oAuth2User.getAttribute("id");
		String username = provider+"_" + providerId;
		String email = oAuth2User.getAttribute("email");
		String password = bCryptPasswordEncoder.encode(cosSecret);
		User userEntity = userRepository.findByUsername(username).orElseGet(new Supplier<User>() {

			@Override
			public User get() {
				User user = User.builder()
						.username(username)
						.password(password)
						.email(oAuth2User.getAttribute("email"))
						.role(UserRole.USER)
						.provider("facebook")
						.providerId(oAuth2User.getAttribute("id"))
						.build();
				return userRepository.save(user);
			}
						
		});	 
			return userEntity;		
		//강제 로그인 진행 -> Authenticatioin객체를 세션에 등록하기
	
	}


}
