package com.cos.instargram.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.instargram.domain.comment.Comment;
import com.cos.instargram.domain.image.Image;
import com.cos.instargram.domain.image.ImageRepository;
import com.cos.instargram.domain.likes.Likes;
import com.cos.instargram.domain.tag.Tag;
import com.cos.instargram.domain.tag.TagRepository;
import com.cos.instargram.domain.user.User;
import com.cos.instargram.domain.user.UserRepository;
import com.cos.instargram.util.Utils;
import com.cos.instargram.web.dto.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final TagRepository tagRepository;
	private final UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public List<Image> 피드사진(int loginUserId, String tag){
		List<Image> images = null;
		if(tag == null || tag.equals("")) {
			images = imageRepository.mFeeds(loginUserId);
		}else {
			images = imageRepository.mFeeds(tag);
		}
		
		for (Image image : images) {
			image.setLikeCount(image.getLikes().size());
			
			//좋아요 상태 여부
			for (Likes like : image.getLikes()) {
				if(like.getUser().getId() == loginUserId) {
					image.setLikeState(true);
				}
			}
			//댓글 주인 여부 등록
			for(Comment comment : image.getComments()) {
				if(comment.getUser().getId() == loginUserId) {
					comment.setCommentHost(true);
				}
			}
			
		}
		return images;
	}
	
	// C:/src/springwork/instargram/src/main/resources/upload/ -> yaml파일에 설정되어있음.	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public void  사진업로드(ImageReqDto imageReqDto, int userId) {
		System.out.println("사진업로드");
		
		User userEntity = userRepository.findById(userId)
				.orElseThrow(null);
		
		UUID uuid = UUID.randomUUID();
		String imageFilename = uuid+""+imageReqDto.getFile().getOriginalFilename();
		Path imageFilepath = Paths.get(uploadFolder+imageFilename);
		try {
			Files.write(imageFilepath, imageReqDto.getFile().getBytes()); //이러면 하드디스크에 저장이 됨.
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
		
		//1. Image 저장
		Image image = imageReqDto.toEntity(imageFilename, userEntity); //꼭 파일명(imageFilepath)만 디비에 넣어야 한다.
		Image imageEntity = imageRepository.save(image);	
		
		// 2. Tag 저장
				List<String> tagNames = Utils.tagParse(imageReqDto.getTags());
				for (String name : tagNames) {
					Tag tag = Tag.builder()
							.image(imageEntity)
							.name(name)
							.build();
					tagRepository.save(tag);
					
				}
	}
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진가져오기(int loginUserId) {
		System.out.println("사진 가져오기");		
		return imageRepository.mNonFollowImage(loginUserId);
	}
	
	
}
