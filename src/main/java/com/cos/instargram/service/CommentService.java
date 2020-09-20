package com.cos.instargram.service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instargram.config.handler.ex.MyImageIdNotFoundException;
import com.cos.instargram.domain.comment.CommentRepository;
import com.cos.instargram.domain.image.Image;
import com.cos.instargram.domain.image.ImageRepository;
import com.cos.instargram.domain.noti.NotiRepository;
import com.cos.instargram.domain.noti.NotiType;
import com.cos.instargram.web.dto.CommentRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final NotiRepository notiRepository;
	private final ImageRepository imageRepository;
	
	@Transactional
	public void 댓글쓰기(CommentRespDto commentRespDto) {
		commentRepository.mSave( 
				commentRespDto.getUserId(), 
				commentRespDto.getImageId(), 
				commentRespDto.getContent());
		
		Image imageEntity = imageRepository.findById(commentRespDto.getImageId()).orElseThrow(new Supplier<MyImageIdNotFoundException>() {
			@Override
			public MyImageIdNotFoundException get() {
				return new MyImageIdNotFoundException();
			}
		});
		System.out.println("댓글쓰기 누가 누구에게 댓글적었는지");
		notiRepository.mSave(commentRespDto.getUserId(), imageEntity.getUser().getId(), NotiType.COMMENT.name());
	}
	
	@Transactional
	public void 댓글삭제(int id) {
		commentRepository.deleteById(id);
	}
}
