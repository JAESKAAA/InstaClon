package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String caption; //사진 설명
	private String postImageUrl; // 사진을 전송받아서 그 사진을 서버 특정 폴더에저장 - DB에는 저장된 경로만 insert할 예정
	
	@JoinColumn(name="userId")
	@ManyToOne
	private User user; //어떤 유저가 올렸는지 확인해야 되니까
	
	/*
	 * [추후 추가 예정]
	 * 
	 * 1. 좋아요 
	 * 2. 댓글
	 * 
	 * */
	
	
	private LocalDateTime createDate; //등록 시간
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
