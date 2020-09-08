package com.cos.instargram.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.instargram.service.CommentService;
import com.cos.instargram.web.dto.CommentRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping("/comment")
	public ResponseEntity<?> comment(CommentRespDto commentRespDto) {
		System.out.println("commentRespDto : "+commentRespDto);
		commentService.댓글쓰기(commentRespDto);
		return new ResponseEntity<String>("ok", HttpStatus.CREATED);
	}
}
