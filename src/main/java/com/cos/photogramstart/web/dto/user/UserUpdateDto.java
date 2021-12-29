package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	
	@NotBlank
	private String name; //필수
	@NotBlank
	private String password; //필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	//website, bio 등의 정보는 필수가 아니라 null이 올 수 있음
	//이는 entity로 변환시 조금 문제가 될 수 있어 추후 코드 수정 예정
	public User toEntity() {
		return User.builder()
				.name(name)
				//만약 패스워드를 작성하지 않으면 DB에 공백의 패스워드가 들어가는 문제점이 있음
				//따라서 validation check 필요
				.password(password)
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
