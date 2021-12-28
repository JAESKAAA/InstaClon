package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthController {

	private final AuthService authService;
	
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {
		
		//Valid를 통한 유효성검사에서 에러가 발생하면 해당 에러들을 스프링이 bindingResult의 FieldErros라는 리스트에 담아줌
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new CustomValidationException("유효성 검사 실패함",errorMap);
		}else {

			log.info(signupDto.toString());
			
			//전달받은 Dto를 User 모델에 저장
			User user = signupDto.toEntity();
			log.info(user.toString());
			
			//서비스단에 회원가입 메소드 호출하여 데이터 넘김
			User userEntity = authService.signUp(user);
			System.out.println(userEntity);
			return "auth/signin";
		}
		
	}
}
