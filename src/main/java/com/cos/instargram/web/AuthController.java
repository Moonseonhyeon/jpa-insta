package com.cos.instargram.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.instargram.service.UserService;
import com.cos.instargram.web.dto.JoinReqDto;

import lombok.RequiredArgsConstructor;



//인증 안된 사람들이 들어올 수 있는 페이지
@Controller
@RequiredArgsConstructor
public class AuthController {

	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	private final UserService userService;
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		log.info("loginForm()진입");
		return "auth/loginForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		log.info("/auth/joinForm 진입"); 
		return "auth/joinForm";
	}
	
	@PostMapping("/auth/join")
	public String join(JoinReqDto joinReqDto) {
		log.info(joinReqDto.toString());
		userService.회원가입(joinReqDto); //트랜젝션 시작
		//여기서 lazy로딩(필요할 때 get으로 select할 수 있음)할 수 있음. yml파일에 설정해서 그렇다. //return할 때 모델말고 resDto만들어서 해야함!
		return "redirect:/auth/loginForm"; //우리 메인 페이지로 가게해야하는데..우선 redirect해서 컨트롤러 찾아감.
	}
	
}

