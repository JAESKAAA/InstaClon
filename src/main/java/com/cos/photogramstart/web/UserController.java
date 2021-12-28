package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;

@Controller
public class UserController {

	@GetMapping("/user/{id}")
	public String profile(@PathVariable String id) {
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable String id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		//어노테이션 사용하여 유저정보 뽑아내기
		System.out.println("세션에서 뽑은 유저아이디 : " + principalDetails.getUsername());
		
		//어노테이션 없이 Authentication 객체 뽑아내는 법
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PrincipalDetails mPrincipalDetails = (PrincipalDetails)auth.getPrincipal();
		System.out.println("직접 찾은 PrincipalDetilas 정보 = " +auth.getPrincipal());
		System.out.println("찾은 세션에서 뽑은 유저정보 = " + mPrincipalDetails.getUser());
		return "user/update";
	}
}
