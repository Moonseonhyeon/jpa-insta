package com.cos.instargram.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instargram.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class LikesService {

	private final LikesRepository likesRepository;
	
	@Transactional
	public void 좋아요(int imageId, int loginUserId) {
		likesRepository.mSave(imageId, loginUserId);
	}
	
	@Transactional
	public void 싫어요(int imageId, int loginUserId) {
		likesRepository.mDelete(imageId, loginUserId);
	}
	
	
}
