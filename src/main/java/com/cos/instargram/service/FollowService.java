package com.cos.instargram.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cos.instargram.domain.follow.FollowRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {
	private final FollowRepository followRepository;

	// 서비스단에서 롤백하려면 throw를 runtimeException을 던져야됨.
	@Transactional
	public void 팔로우(int loginUserId, int pageUserId) {
		int result = followRepository.mFollow(loginUserId, pageUserId);
		System.out.println("팔로우 result : "+result);
	}

	@Transactional
	public void 팔로우취소(int loginUserId, int pageUserId) {	
		int result = followRepository.mUnFollow(loginUserId, pageUserId);
		System.out.println("팔로우취소 result : "+result);
	}
}