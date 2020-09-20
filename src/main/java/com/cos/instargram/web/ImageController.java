package com.cos.instargram.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.instargram.config.auth.CosAnnotation;
import com.cos.instargram.config.auth.LoginUserAnnotation;
import com.cos.instargram.config.auth.dto.LoginUser;
import com.cos.instargram.domain.image.Image;
import com.cos.instargram.service.ImageService;
import com.cos.instargram.web.dto.ImageReqDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;
	
	@GetMapping({" ", "/", "/image/feed"})
	public String feed(
			String tag,
			@LoginUserAnnotation LoginUser loginUser,
			Model model
			//@AuthenticationPrincipal PrincipalDetails principal, HttpSession session
			) {		
		//System.out.println(principal.getUser());
		//System.out.println("@AuthenticationPrincipal : "+ principal.getUser());
		//LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
		System.out.println("loginUser : " + loginUser);
		//System.out.println("cosAnnotation : "+cos);
		model.addAttribute("images", imageService.피드사진(loginUser.getId(), tag));
		return "image/feed";
	}
	
	@GetMapping({"/test/image/feed"})
	public @ResponseBody List<Image> testFeed(
			String tag,
			@LoginUserAnnotation LoginUser loginUser
			) {
		List<Image> images = imageService.피드사진(loginUser.getId(), tag);
		return images;
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
		imageService.사진업로드(imageReqDto, loginUser.getId());		
		return "redirect:/user/"+loginUser.getId();
	}
	
	@GetMapping("/image/explore")
	public String imageExplore(@LoginUserAnnotation LoginUser loginUser, Model model) {		
		model.addAttribute("images", imageService.인기사진가져오기(loginUser.getId()));
		return "image/explore";
	}
	
}
