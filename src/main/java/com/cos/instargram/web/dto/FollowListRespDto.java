package com.cos.instargram.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowListRespDto {
	private int id;
	private String name;
	private String username;
	private String profileImage;	
	private Boolean followState;
	private Boolean equalUserState;
}
