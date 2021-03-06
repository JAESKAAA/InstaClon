package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UserService userService;
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
								@PathVariable int id,
								@Valid UserUpdateDto userUpdateDto,
								BindingResult bindingResult, //꼭 @Valid가 적혀있는 다음 파라미터에 위치해야함
								@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationException("유효성 검사 실패함",errorMap);
		}else {
			System.out.println(userUpdateDto);
			User userEntity = userService.updateUser(id, userUpdateDto.toEntity());
			
			//세션의 유저를 변경된 유저정보로 바꿔줌
			principalDetails.setUser(userEntity);
			
			/*
			 * [하기 return에서 무한참조 발생 이유]
			 * 1. update.js에서 응답 데이터를 json으로 요청
			 * 2. userEntity가 객체이기 때문에 MessageConvertor가 해당 필드의 모든 Getter함수를 호출하여 JSON으로 파싱하는 과정을 거침
			 * 3. 이때, userEntity안에 Image를 참조하게 되고, Image에서는 User를 다시 참조하면서 무한 참조가 발생
			 * 
			 * */
			
			return new CMRespDto<>(1, "회원수정완료", userEntity);

		}
	}
}
