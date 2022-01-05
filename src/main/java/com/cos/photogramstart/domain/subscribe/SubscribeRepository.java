package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

	/*
	 * [주의!] 
	 * 네이티브 쿼리 사용시 JPA 메소드를 쓰는게 아니기 때문에 @PrePersist 같은 어노테이션이 동작하지 않음.
	 * 따라서 날짜도 따로 넣어어줘야함 
	 * nativeQuery = true 설정 잊지말기
	 * 
	 * 특이한 점은 변수 바인딩을 콜론(:)으로 한다는점
	 * ex. :fromUserId ==> 매개변수의 fromUserId가 :fromUserId 선언된 곳에 들어가게 됨
	 * 
	 * @Modifying : 데이터베이스에 변경을 주는 native 쿼리 사용시 꼭 선언해줘야 정상 작동함 (INSERT, UPDATE, DELETE)
	 * 
	 * 메소드 타입을 int로 준 것은 JDBC의 preparedStatement처럼 변경된 행의 갯수만큼 숫자가 리턴되기 떄문 (-1은 오류발생을 의미)
	 * 하지만, 에러 발생시 try/catch 만들어서 예외 핸들러에서 처리하는게 낫기 때문에 void로 다시 바꿈
	 * 
	 * */
	
	@Modifying
	@Query(value= "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())"
			, nativeQuery = true)
	void mSubscribe(int fromUserId, int toUserId);
	
	@Modifying
	@Query(value= "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId"
			, nativeQuery = true)
	void mUnsubscribe(int fromUserId, int toUserId);
	
	//select 쿼리이기 때문에 Modifying은 설정 안해줘도 됨
	@Query(value= "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery = true)
	int mSubscribeCount(int pageUserId);
	
	@Query(value= "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId= :pageUserId", nativeQuery = true)
	int mSubscribeState(int principalId, int pageUserId);
	
	
}
