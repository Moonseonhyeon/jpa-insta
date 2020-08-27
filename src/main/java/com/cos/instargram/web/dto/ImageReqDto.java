package com.cos.instargram.web.dto;


import org.springframework.web.multipart.MultipartFile;

import com.cos.instargram.domain.image.Image;
import com.cos.instargram.domain.user.User;

import lombok.Data;

@Data
public class ImageReqDto {
	private MultipartFile file;
	private String caption;
	private String location;
	private String tags;
	
	public Image toEntity(String imageUrl, User userEntity) {
		return Image.builder()
				.location(location)
				.caption(caption)
				.imageUrl(imageUrl)
				.user(userEntity)
				.build();
	}

}
