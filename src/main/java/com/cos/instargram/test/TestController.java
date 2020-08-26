package com.cos.instargram.test;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.instargram.config.auth.PrincipalDetails;
import com.cos.instargram.config.handler.ex.MyUsernameNotFoundException;

@Controller
public class TestController {
	
	@GetMapping("/test/facebook")
	public @ResponseBody String facebook(
			Authentication authentication
			) {
		System.out.println("authentication : "+ authentication.getPrincipal()); //PrincipalDetails라는 거다!
		System.out.println("authentication : "+ authentication.getDetails());
		//PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal(); //구글로그인 하면 OAuth2User타입임.
		OAuth2User oauth2user = (OAuth2User)authentication.getPrincipal();		
		System.out.println("authentication : "+ oauth2user.getAttributes()); // 구글 로그인
		//System.out.println("authentication : "+ principalDetails.getUser());
		return "facebook 로그인 완료";
	}
	
	
	@GetMapping("/test/facebook2")
	public @ResponseBody String facebook2(
			@AuthenticationPrincipal UserDetails principal
			) {
		System.out.println(principal.getUsername());		
		return "facebook 로그인 완료2";
	}

	@GetMapping("/test/login")
	public String test1() {
		return "auth/login";
	}
	
	@GetMapping("/test/join")
	public String test2() {
		return "auth/join";
	}
	
	@GetMapping("/test/following")
	public String test3() {
		return "follow/following";
	}
	
	@GetMapping("/test/explore")
	public String test4() {
		return "image/explore";
	}
	
	@GetMapping("/test/feed")
	public String test5() {
		return "image/feed";
	}
	
	@GetMapping("/test/imageUpload")
	public String test6() {
		return "image/image-upload";
	}
	
	@GetMapping("/test/profileEdit")
	public String test7() {
		return "user/profile-edit";
	}
	
	@GetMapping("/test/profile")
	public String test8() {
		return "user/profile";
	}

	@GetMapping("/test/username/{username}")
	public @ResponseBody String test9(@PathVariable String username) {
		if(!username.equals("ssar")) {
			throw new MyUsernameNotFoundException("유저네임 못찾음");
		}

		return "username test";
	}
}
