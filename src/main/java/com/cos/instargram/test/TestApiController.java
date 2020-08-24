package com.cos.instargram.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.instargram.domain.image.Image;
import com.cos.instargram.domain.image.ImageRepository;
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
	
	@GetMapping("/test/api/join")
	public User join() {
		User user = User.builder()
				.name("최주호")
				.password("1234")
				.phone("0102222")
				.bio("안녕 난 코스야")
				.role(UserRole.USER)
				.build();
		User userEntity = userRepository.save(user);
				return userEntity;
	}
	
	@GetMapping("/test/api/image")
	public String image() {
		User userEntity = userRepository.findById(1).get(); //영속화 -> 세션에서 user가져오기
		
		Image image = Image.builder()
				.location("다낭")
				.caption("설명")
				.user(userEntity)				
				.build();
		
		Image imageEntity = imageRepository.save(image);
		
		List<Tag> tags = new ArrayList<>();
		Tag tag1 = Tag.builder()
				.name("#다낭")
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
	
	
}
