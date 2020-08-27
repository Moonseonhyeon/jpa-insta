package com.cos.instargram.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.instargram.config.auth.CosAnnotation;
import com.cos.instargram.config.auth.LoginUserAnnotation;

import com.cos.instargram.config.auth.dto.LoginUser;


@Controller
public class ImageController {
	
	@GetMapping({" ", "/",  "/image/feed"})
	public String feed(
			@LoginUserAnnotation LoginUser loginUser,
			@CosAnnotation String cos
			//@AuthenticationPrincipal PrincipalDetails principal, HttpSession session
			) {
		//System.out.println(principal.getUser());
		//System.out.println("@AuthenticationPrincipal : "+ principal.getUser());
		//LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
		System.out.println("loginUser : " + loginUser);
		System.out.println("cosAnnotation : "+cos);
		return "image/feed";
	}
	
}
