package com.cos.instargram.domain.likes;

import java.sql.Timestamp;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.instargram.domain.image.Image;

import com.cos.instargram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

		
	 //ManyToMany가 있는데 실무에서 잘 쓸수가 없어요. N:N 한명의 유저는 한명의 유저는 여러명의 유저를 follow할 수 있다. 
	//jpa가 새로운 테이블(중간 테이블)을 하나더 만들어 줌. 근데 날짜 같은거나 추가적인 데이터를 테이블에 못넣어서 만듬. 
	//그래서 내가 테이블 하나만들어서 서로서로 ManyToOne을 걸어요. 그래서 추가적으로 colum을 넣어요. Many(userID)ToOne(중간 테이블)
	
	@ManyToOne //기본값 EAGER
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "imageId")
	private Image image;
	
	@CreationTimestamp
	private Timestamp createDate;
}
