package com.cos.instargram.service;


import java.util.List;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instargram.config.auth.dto.LoginUser;
import com.cos.instargram.config.handler.ex.MyUserIdNotFoundException;
import com.cos.instargram.domain.image.Image;
import com.cos.instargram.domain.image.ImageRepository;
import com.cos.instargram.domain.user.User;
import com.cos.instargram.domain.user.UserRepository;
import com.cos.instargram.web.dto.JoinReqDto;
import com.cos.instargram.web.dto.UserProfileImageRespDto;
import com.cos.instargram.web.dto.UserProfileRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	
	@PersistenceContext
	EntityManager em;
	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public void 회원가입(JoinReqDto joinReqDto) {
		String encPassword = 
				bCryptPasswordEncoder.encode(joinReqDto.getPassword());
		joinReqDto.setPassword(encPassword);
		userRepository.save(joinReqDto.toEntity());
	}
	
	
	// 읽기 전용 트랜잭션
	// (1) 변경 감지 연산을 하지 않음.
	// (2) isolation(고립성)을 위해 Phantom read 문제가 일어나지 않음.
	@Transactional(readOnly = true)
	public UserProfileRespDto 회원프로필(int id, LoginUser loginUser) {

		int imageCount;
		int followerCount;
		int followingCount;

		User userEntity = userRepository.findById(id)
				.orElseThrow(new Supplier<MyUserIdNotFoundException>() {
					@Override
					public MyUserIdNotFoundException get() {
						return new MyUserIdNotFoundException();
					}
				});

		// 1. 이미지들과 전체 이미지 카운트
		StringBuilder sb = new StringBuilder();
		sb.append("select im.id, im.imageUrl, ");
		sb.append("(select count(*) from likes lk where lk.imageId = im.id) as likeCount, ");
		sb.append("(select count(*) from comment ct where ct.image_id = im.id) as commentCount ");
		sb.append("from image im where im.userId = ? ");
		String q = sb.toString();
		Query query = em.createNativeQuery(q, "UserProfileImageRespDtoMapping").setParameter(1, id); //em이 영속화 됐음. //영속화를 없애는 방법도 있음. em.detach(imagesEntity); -> 이런씩으로 em으로 영속화 관리를 할 수 있음.
		List<UserProfileImageRespDto> imagesEntity = query.getResultList(); //
		
		//em
		//persistance context 에 없으면 persist()시킴(select), 이미 있으면 persistance context 여기서 가져옴. 
		//detach() 준영속 - 잠시 persistance context에서 잠시 뺏다가 다시 넣을 수 있음.
		//remove() 영속화 없애는 일 해줌.
        //이렇게 캐싱해줌!		
		//물론 DB에도 캐싱하고 있지만 em이 관리 해주니까 IO를 줄여준다.
		
		//통계쿼리가 필요하면 mybatis 아니면 jpa쓰기
		//JPA + QueryDSL 복잡한 쿼리 쓰고 싶으면 쓰세요!
		
		imageCount = imagesEntity.size();
		
		// 2. 팔로우 수 (수정해야됨)
		followerCount = 50;
		followingCount = 100;

		// 3. 최종마무리
		UserProfileRespDto userProfileRespDto = 
				UserProfileRespDto.builder()
				.pageHost(id==loginUser.getId())
				.followerCount(followerCount)
				.followingCount(followingCount)
				.imageCount(imageCount)
				.user(userEntity)
				.images(imagesEntity)
				.build();

		return userProfileRespDto;		
		//이렇게 Dto를 리턴하면 장점은 lazyload가 일어나지 않아서 무한 참조 하지 않게 할 수 있음.
	}
}