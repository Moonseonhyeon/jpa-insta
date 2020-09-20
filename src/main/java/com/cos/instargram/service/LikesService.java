package com.cos.instargram.service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instargram.config.handler.ex.MyImageIdNotFoundException;
import com.cos.instargram.domain.image.Image;
import com.cos.instargram.domain.image.ImageRepository;
import com.cos.instargram.domain.likes.LikesRepository;
import com.cos.instargram.domain.noti.NotiRepository;
import com.cos.instargram.domain.noti.NotiType;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class LikesService {

	private final LikesRepository likesRepository;
	private final ImageRepository imageRepository; 
	private final NotiRepository notiRepository;
	
	@Transactional
	public void 좋아요(int imageId, int loginUserId) {
		likesRepository.mSave(imageId, loginUserId);
		Image imageEntity = imageRepository.findById(imageId)
				.orElseThrow(new Supplier<MyImageIdNotFoundException>(){
				
					@Override
				public MyImageIdNotFoundException get() {
					return new MyImageIdNotFoundException();
				}				
		});
		notiRepository.mSave(loginUserId, imageEntity.getUser().getId(), NotiType.LIKE.name());
	}
	
	@Transactional
	public void 싫어요(int imageId, int loginUserId) {
		likesRepository.mDelete(imageId, loginUserId);
	}
	
	
}
