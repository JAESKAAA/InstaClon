package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //DB에 테이블 생성
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
					name = "subscribe_uk", //제약조건 이름	
					columnNames = {"fromUserId","toUserId"} //컬럼명은 실제 DB에 설정된 컬럼이름으로 잡아줘야함
						)
			}
		)
public class Subscribe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@JoinColumn(name = "fromUserId")
	@ManyToOne 
	private User fromUser; //구독하는 유저 (N:1의 관계)
	
	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser; //구독받는 유저 (N:1의 관계)
	

	private LocalDateTime createDate;
	
	@PrePersist //디비에 INSERT 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
