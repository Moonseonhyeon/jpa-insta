package com.cos.instargram.web;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.instargram.config.auth.LoginUserAnnotation;
import com.cos.instargram.config.auth.dto.LoginUser;
import com.cos.instargram.domain.follow.FollowRepository;
import com.cos.instargram.service.FollowService;
import com.cos.instargram.web.dto.FollowListRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FollowController {	

	private final FollowService followService;
	

	@PostMapping("/follow/{id}")
	public ResponseEntity<?> follow(@PathVariable int id,
			@LoginUserAnnotation LoginUser loginUser){
		followService.팔로우(loginUser.getId(), id);
		return  new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@DeleteMapping("/follow/{id}")
	public ResponseEntity<?> unFollow(@PathVariable int id,
			@LoginUserAnnotation LoginUser loginUser) {
		followService.팔로우취소(loginUser.getId(), id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);		

	}
	
	@GetMapping("/follow/followerList/{id}") //이 페이지의 유저의 아이디
	public String followerList(@LoginUserAnnotation LoginUser loginUser, @PathVariable int id, Model model) {
		model.addAttribute("users", followService.팔로워리스트(loginUser.getId(), id));
		return "follow/follower-list";
	}
	
	@GetMapping("/follow/followingList/{id}") //이 페이지의 유저의 아이디
	public @ResponseBody List<FollowListRespDto>  followingList(@LoginUserAnnotation LoginUser loginUser, @PathVariable int id, Model model) {
		//model.addAttribute("users", followService.팔로잉리스트(loginUser.getId(), id));
		return followService.팔로잉리스트(loginUser.getId(), id);
	}


}
