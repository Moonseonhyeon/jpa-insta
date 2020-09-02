package com.cos.instargram.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.cos.instargram.domain.follow.FollowRepository;
import com.cos.instargram.domain.user.User;
import com.cos.instargram.web.dto.FollowListRespDto;

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
	
//	@Transactional(readOnly = true)
//	public List<FollowListRespDto> 팔로워리스트(int loginUserId, int pageUserId) {
//			
//		StringBuilder sb = new StringBuilder();
//		sb.append("SELECT u.id, u.name, u.username, u.profileImage, ");
//		sb.append("(SELECT true FROM follow f2 ");
//		sb.append("WHERE f2.fromUserID = ? AND f2.toUserId in ( ");
//		sb.append("SELECT u.id ");
//		sb.append("FROM user u INNER JOIN follow f1 ");
//		sb.append("ON u.id = f1.fromUserId ");
//		sb.append("WHERE f1.toUserId = ? ");
//		sb.append(")) as matpal ");
//		sb.append("FROM user u INNER JOIN follow f1 ");
//		sb.append("ON u.id = f1.fromUserId ");
//		sb.append("WHERE f1.toUserId = ? ");
//		
//		String q = sb.toString();
//		Query query = em.createNativeQuery(q, "FollowerListDtoMapping")
//				.setParameter(1, loginUserId)
//				.setParameter(2, pageUserId)
//				.setParameter(3, pageUserId);
//		List<FollowListRespDto> followerListIEntity = query.getResultList(); 
//		return followerListIEntity;
//	}
	
	public List<FollowListRespDto> 팔로워리스트(int loginUserId, int pageUserId){
		// 첫번째 물음표 loginUserId, 두번째 물음표 pageUserId
		StringBuilder sb = new StringBuilder();
		sb.append("select u.id,u.username,u.name,u.profileImage, ");
		sb.append("if(u.id = ?, true, false) equalUserState,");
		sb.append("if((select true from follow where fromUserId = ? and toUserId = u.id), true, false) as followState ");
		sb.append("from follow f inner join user u on f.fromUserId = u.id ");
		sb.append("and f.toUserId = ?");
		String q = sb.toString();

		Query query = em.createNativeQuery(q, "FollowListDtoMapping")
				.setParameter(1, loginUserId)
				.setParameter(2, loginUserId)
				.setParameter(3, pageUserId);
		List<FollowListRespDto> followerListEntity = query.getResultList();
		return followerListEntity;
	}
	
	
	public List<FollowListRespDto> 팔로잉리스트(int loginUserId, int pageUserId){
		// 첫번째 물음표 loginUserId, 두번째 물음표 pageUserId
		StringBuilder sb = new StringBuilder();
		sb.append("select u.id,u.username,u.name,u.profileImage, ");
		sb.append("if(u.id = ?, true, false) equalUserState,");
		sb.append("if((select true from follow where fromUserId = ? and toUserId = u.id), true, false) as followState ");
		sb.append("from follow f inner join user u on f.toUserId = u.id ");
		sb.append("and f.fromUserId = ?");
		String q = sb.toString();

		Query query = em.createNativeQuery(q, "FollowListDtoMapping")
				.setParameter(1, loginUserId)
				.setParameter(2, loginUserId)
				.setParameter(3, pageUserId);
		List<FollowListRespDto> followListEntity = query.getResultList();
		return followListEntity;
	}
	
	
}