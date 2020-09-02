package com.cos.instargram.domain.user;


import java.sql.Timestamp;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.instargram.web.dto.FollowListRespDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//spring으로 회귀
@SqlResultSetMapping(
		name = "FollowerListDtoMapping",
		classes = @ConstructorResult(
			targetClass = FollowListRespDto.class,
			columns = {
					@ColumnResult(name="id", type = Integer.class),
					@ColumnResult(name="name", type = String.class),
					@ColumnResult(name="username", type = String.class),
					@ColumnResult(name="profileImage", type = String.class),
					@ColumnResult(name="matpal", type = String.class)
					//원시자료형만 가능 오브젝트는 안됨.
			}
			)
		)


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String username;
	private String password;
	private String name;
	private String website;
	private String bio; //자기소개
	private String email;	//모델 만들 때 주석처리 한거 품.
	private String phone;
	private String gender;
	private String profileImage;
	@Enumerated(EnumType.STRING) //db는 오브젝트가 없으니까 이거 그냥 스트링으로 알으라고 디비한테 알려주는 것임.
	private UserRole role;
	private String provider;
	private String providerId;
	@CreationTimestamp
	private Timestamp createDate;		
}
