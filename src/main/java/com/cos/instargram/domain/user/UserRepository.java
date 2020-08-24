package com.cos.instargram.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository가 extends되면 IoC가 자동으로 됩니다. @Repository가 필요없음.  jpa가 CRUD함수를 다 가지고 있다.
public interface UserRepository extends JpaRepository<User, Integer>{

}
