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

import com.cos.instargram.domain.image.Image;
import com.cos.instargram.domain.image.ImageRepository;
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
	
	// C:/src/springwork/instargram/src/main/resources/upload/ -> yaml파일에 설정되어있음.	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public void  사진업로드(ImageReqDto imageReqDto, int userId) {
		
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
		Image image = imageReqDto.toEntity(imageFilename, userEntity); //꼭 파일명만 디비에 넣어야 한다.
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
	
}
