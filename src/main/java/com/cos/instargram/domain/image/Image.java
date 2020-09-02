package com.cos.instargram.domain.image;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.instargram.domain.tag.Tag;
import com.cos.instargram.domain.user.User;
import com.cos.instargram.web.dto.UserProfileImageRespDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//spring으로 회귀
@SqlResultSetMapping(
		name = "UserProfileImageRespDtoMapping",
		classes = @ConstructorResult(
			targetClass = UserProfileImageRespDto.class,
			columns = {
					@ColumnResult(name="id", type = Integer.class),
					@ColumnResult(name="imageUrl", type = String.class),
					@ColumnResult(name="likeCount", type = Integer.class),
					@ColumnResult(name="commentCount", type = Integer.class)
					//원시자료형만 가능 오브젝트는 안됨.
			}
			)
		)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String location;
	private String caption;	
	private String imageUrl;
	
	//Image를 select할 때 한명의 유저가 딸려옴. 부하가 적어요. 내가 안적어도 기본전략이 EAGER. ( select할 때 기본적으로 user 오브젝트
	@ManyToOne(fetch = FetchType.EAGER) //많은 이미지는 하나의 유저를 가진다.
	@JoinColumn(name = "userId") //컬럼명 //타입은 User오브젝트의 PK타입 //외례키(FK)걸어진다. //이렇게 컬럼명 안알려주면 여기서는 user이라고 변수명을 따른다.
	private User user; //오브젝트랑 relation. 실제 DB에는 userId들어가지만 //select할 때는 오브젝트
	
	//Image를 select하면 여러개의 Tag가 딸려옴. 부하가 커요. 내가 안적어도 기본전략이 LAZY. (select할 때 기본적으로 null 하지만 내가 getTags()호출하면 디비에 select해서 영속성으로 해서 가져온다. 이렇게 lazyload하면 view 단에서 부하가 적다.)
	@OneToMany(mappedBy = "image", fetch = FetchType.LAZY) //하나의 이미지에 많은 태그  //나는 연관관계주인이 아니다. 외례키가(FK)가 아니고 연관관계의 주인은 imageId이다. 이 어노테이션에 설정을 자바가 알고있는 변수명인 image를 적어주세요! //FK의 주인을 정하는 어노테이션이다. 
	@JsonIgnoreProperties({"image"}) //이렇게 image를 select할 때 접근할 때 Tag내부의 image는 json이 getter호출 하지마~! //Jackson한테 내리는 명령어 //무함참조 막아주는 방법임.
	private List<Tag> tags;
	
	@CreationTimestamp
	private Timestamp createDate;
	

}
