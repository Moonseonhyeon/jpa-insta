package com.cos.instargram.domain.follow;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.instargram.domain.image.Image;
import com.cos.instargram.domain.like.Like;
import com.cos.instargram.domain.like.Like.LikeBuilder;
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
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne // EAGER 왜냐면 이게 collection이 아니니까
	@JoinColumn(name = "fromUser")
	private User fromUser;
	
	@ManyToOne
	@JoinColumn(name = "toUser")
	private User toUser;
	
	//@ManyToMany 가 있으면 중간테이블 저절로 만들어지지만 추가적으로 컬럼을 내가 만들수가 없음.
	//한방쿼리로 짜야한다.
	
	@CreationTimestamp
	private Timestamp createDate;
}
