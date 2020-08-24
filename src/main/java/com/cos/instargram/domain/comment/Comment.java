package com.cos.instargram.domain.comment;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.instargram.domain.follow.Follow;
import com.cos.instargram.domain.follow.Follow.FollowBuilder;
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
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String content;
	
	@ManyToOne
	private Image image;
	
	@ManyToOne
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
