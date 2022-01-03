package com.cos.photogramstart.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;

	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public void imageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		
		UUID uuid = UUID.randomUUID(); // 고유의 id를 부여하기 위함
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); //혹시라도 UUID가 겹치는것을 방지하고자 파일명을 뒤에 이어붙여줌
		System.out.println("이미지파일 이름 => "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		//upload폴더 생성 (해당 폴더가 존재하면 false리턴하고 생성하지 않음)
		File file = new File(uploadFolder);
		boolean isCreate = file.mkdir();
		
		System.out.println("폴더 생성 여부 => "+isCreate);
		
		//통신 및 I/O가 일어날때는 예외가 발생할 수 있고 이런것들은 런타임시에만 잡을 수 있기 때문에 꼭 try/catch 필요 !!
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes()); //(path, 실제파일, 옵션(생략가능))
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//image테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
	
	//	System.out.println(imageEntity);
	}
}
