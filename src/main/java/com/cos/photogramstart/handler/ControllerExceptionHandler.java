package com.cos.photogramstart.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		
		/*
		 * [CMRespDto vs Script 방식 비교]
		 * 1. 클라이언트에게 응답할때는 Script 방식이 좋음 // 브라우저가 받음
		 * 2. Ajax 통신  - CMRespDto //응답을 개발자가 응답을 받으므로
		 * 3. Android 통신 - CMRespDto //안드로이드 앱에서 개발자가 응답을 받음
		 * */ 
		return Script.back(e.getErrorMap().toString());
	}
}
