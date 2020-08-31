package com.cos.instargram.web.dto;

import java.util.List;

import com.cos.instargram.domain.image.Image;
import com.cos.instargram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRespDto {
	private boolean pageHost; //페이지 주인 확인
	private User user;
	private List<Image> images;
	private int followerCount;
	private int followingCount;
	private int imageCount;

}
