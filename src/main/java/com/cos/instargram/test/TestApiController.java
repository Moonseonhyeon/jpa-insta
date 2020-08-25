package com.cos.instargram.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.instargram.domain.follow.Follow;
import com.cos.instargram.domain.follow.FollowRepository;
import com.cos.instargram.domain.image.Image;
import com.cos.instargram.domain.image.ImageRepository;
import com.cos.instargram.domain.likes.Likes;
import com.cos.instargram.domain.likes.LikesRepository;
import com.cos.instargram.domain.tag.Tag;
import com.cos.instargram.domain.tag.TagRepository;
import com.cos.instargram.domain.user.User;
import com.cos.instargram.domain.user.UserRepository;
import com.cos.instargram.domain.user.UserRole;

@RestController
public class TestApiController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private FollowRepository followRepository;
	
	@Autowired
	private LikesRepository likeRepository;
	
	@PostMapping("/test/api/join")
	public User join(@RequestBody User user) {
		
		user.setRole(UserRole.USER); //USER
		/*
		 * User user = User.builder() .name("최주호") .password("1234") .phone("0102222")
		 * .bio("안녕 난 코스야") .role(UserRole.USER) .build();
		 */
		User userEntity = userRepository.save(user);
				return userEntity;
	}
	
	@PostMapping("/test/api/image/{caption}")
	public String image(@PathVariable String caption) {
		User userEntity = userRepository.findById(2).get(); //영속화 -> 세션에서 user가져오기
		
		Image image = Image.builder()
				.location("외국")
				.caption(caption)
				.user(userEntity)				
				.build();
		
		Image imageEntity = imageRepository.save(image);
		
		List<Tag> tags = new ArrayList<>();
		Tag tag1 = Tag.builder()
				.name("#외국")
				.image(imageEntity)
				.build();
		Tag tag2 = Tag.builder()
				.name("#여행")
				.image(imageEntity)
				.build();
				tags.add(tag1);				
				tags.add(tag2);				
				
				//save(x) -> saveAll(o)
				tagRepository.saveAll(tags); 
		
		return "Image Insert 잘됨. " ; //Message converter의 jackson 자바를 json오브젝트 jackson은 getter호출해
	}
	
	
		@GetMapping("/test/api/image/list")
		public List<Image> list(){
			return imageRepository.findAll();
		}
		@GetMapping("/test/api/tag/list")
		public List<Tag> tagList(){
			return tagRepository.findAll();
		}
		
		@PostMapping("/test/api/follow/{fromUserId}/{toUserId}")
		public String follow(@PathVariable int fromUserId, @PathVariable int toUserId) {
			User fromUserIdEntity = userRepository.findById(fromUserId).get();
			User toUserIdEntity = userRepository.findById(toUserId).get();
			
			Follow follow = Follow.builder()
					.fromUser(fromUserIdEntity)
					.toUser(toUserIdEntity)
					.build();
			followRepository.save(follow);
			//http://localhost:8080/test/api/follow/1/2
			return fromUserIdEntity.getUsername()+"이 "+toUserIdEntity.getUsername()+"을 팔로우 했습니다.";		
			
		}
		
			
		@PostMapping("/test/api/like/{imageId}/like")
		public String like (@PathVariable int imageId) {
			Image imageEntity = imageRepository.findById(imageId).get();
			User userEntity = userRepository.findById(3).get();
			
			Likes likes = Likes.builder()
					.user(userEntity)					
					.image(imageEntity)
					.build();
			
			likeRepository.save(likes);
			return userEntity.getUsername()+"이 "+imageEntity.getLocation()+"을 좋아요했습니다.";			
		}
	
		
	
}
