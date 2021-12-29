package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException{
	
	//시리얼 번호는 객체를 구분할때 사용함 (JVM한테 중요한 요소임)
	private static final long serialVersionUID = 1L;

	private Map<String, String> errorMap;

	public CustomValidationException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}
}
