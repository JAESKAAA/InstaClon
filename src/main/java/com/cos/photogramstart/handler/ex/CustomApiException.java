package com.cos.photogramstart.handler.ex;

public class CustomApiException extends RuntimeException{
	
	//시리얼 번호는 객체를 구분할때 사용함 (JVM한테 중요한 요소임)
	private static final long serialVersionUID = 1L;

	
	public CustomApiException(String message) {
		super(message);
	}
}
