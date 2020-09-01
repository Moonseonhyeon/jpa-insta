package com.cos.instargram.domain.follow;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface  FollowRepository extends JpaRepository<Follow, Integer>{
	
	@Query(value="SELECT count(*) FROM follow WHERE toUserId = ?1", nativeQuery = true)
	int mCountByFollower(int userId);
	
	@Query(value="SELECT count(*) FROM follow WHERE fromUserId = ?1", nativeQuery = true)
	int mCountByFollowing(int userId);
	
	@Query(value="SELECT count(*) FROM follow WHERE fromUserId= ?1 AND toUserId = ?2", nativeQuery = true)
	int mFollowState(int loginId, int pageUserId); //이 페이지 주인을 내가 팔로우 하고 있는지 아닌지.

	//수정, 삭제, 추가 시에는 @Modifying필요 (javax 임포트)	
	
	@Modifying
	@Query(value = "INSERT INTO follow(fromUserId, toUserId) VALUES(?1, ?2)", nativeQuery = true)
	int mFollow(int loginUserId, int pageUserId);

	//언팔
	@Modifying
	@Query(value = "DELETE FROM follow WHERE fromUserId = ?1 AND toUserId = ?2", nativeQuery = true)
	int mUnFollow(int loginUserId, int pageUserId);

	

}
