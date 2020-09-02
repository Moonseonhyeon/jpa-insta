package com.cos.instargram.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.cos.instargram.config.auth.CosAnnotation;
import com.cos.instargram.config.auth.LoginUserAnnotation;
import com.cos.instargram.config.auth.dto.LoginUser;
import com.cos.instargram.service.ImageService;
import com.cos.instargram.web.dto.ImageReqDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService ImageService;
	
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
	
	//세션이 있어서 id값 안 받아도 됨
	@GetMapping("/image/uploadForm")
	public String imageUploadForm() {
		return "image/image-upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(
			@LoginUserAnnotation LoginUser loginUser,
			ImageReqDto imageReqDto) {		
		ImageService.사진업로드(imageReqDto, loginUser.getId());		
		return "redirect:/";
	}
	
	@GetMapping("/image/explore")
	public String imageExplore() {
		return "image/explore";
	}
	
}
