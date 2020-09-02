package com.cos.instargram.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.cos.instargram.domain.follow.FollowRepository;
import com.cos.instargram.domain.user.User;
import com.cos.instargram.web.dto.FollowerListRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {
	
	@PersistenceContext
	EntityManager em;
	
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
	
	@Transactional(readOnly = true)
	public List<FollowerListRespDto> 팔로워리스트(int loginUserId) {
			
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT  u.id, u.name, u.username, ");
		sb.append("(select true ");
		sb.append("from follow f2 ");
		sb.append("where f1.fromUserId = f2.toUserId ");
		sb.append("and f1.toUserId = f2.fromUserId) as matpal ");
		sb.append("FROM user u INNER JOIN follow f1 ");
		sb.append("ON u.id = f1.toUserId ");
		sb.append("AND fromUserId = ? ");
		
		String q = sb.toString();
		Query query = em.createNativeQuery(q, "FollowerListDtoMapping").setParameter(1, loginUserId);
		List<FollowerListRespDto> followerListIFollowEntity = query.getResultList(); 
		return followerListIFollowEntity;
	}
	
	
	
	
}