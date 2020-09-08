package com.cos.instargram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instargram.domain.comment.CommentRepository;
import com.cos.instargram.web.dto.CommentRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	@Transactional
	public void 댓글쓰기(CommentRespDto commentRespDto) {
		commentRepository.mSave( 
				commentRespDto.getUserId(), 
				commentRespDto.getImageId(), 
				commentRespDto.getContent());
	}
	
	@Transactional
	public void 댓글삭제(int id) {
		commentRepository.deleteById(id);
	}
}
