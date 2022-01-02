package com.cos.photogramstart.web.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {
	
	private MultipartFile file;
	private String caption;

	
	public Image toEntity(String postImageUrl, User user) {
		return Image.builder()
				.caption(caption) //dto의 캡션을 Image 엔티티만들때 넣어줌
				.postImageUrl(postImageUrl) //c:dev...같은 경로는 yml파일에 설정 되어있으므로 UUID붙은 파일네임만 받기
				.user(user)
				.build();
	}
}
