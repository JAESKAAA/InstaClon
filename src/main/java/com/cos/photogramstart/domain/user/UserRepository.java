package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository <대상객체타입, 객체의 PK값의 데이터 타입> / JpaRepository 상속시 어노테이션 없어도 IoC 등록이 자동으로 됨
public interface UserRepository extends JpaRepository<User, Integer>{

	//JPA query method
	User findByUsername(String username);
}
