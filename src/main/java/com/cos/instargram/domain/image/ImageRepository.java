package com.cos.instargram.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{
	List<Image>findByUserId(int userId);
	
	@Query(value="SELECT * " + 
			"FROM image " + 
			"WHERE userId In " + 
			"(SELECT id FROM user WHERE id NOT IN (?1, " + 
			"(SELECT toUserId FROM follow WHERE fromUserId = ?1))) limit 20 ", nativeQuery = true) //이미지 최대 20개
	List<Image> mNonFollowImage(int loginUserId);

@Query(value = "SELECT * FROM image WHERE userId in (SELECT toUserId FROM follow WHERE fromUserId = ?1)", nativeQuery = true)
List<Image> mFeeds(int loginUserId);	
	
}
