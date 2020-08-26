package com.cos.instargram.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.instargram.config.auth.PrincipalDetails;

@Controller
public class ImageController {

	
	@GetMapping({" ", "/",  "/image/feed"})
	public String feed(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println(principal.getUser());
		return "image/feed";
	}
	
	
	
}
