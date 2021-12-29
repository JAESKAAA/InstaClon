package com.cos.photogramstart.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {
	
	private final SubscribeService subscribeService;

	/* [구독 기능 관련]
	 * 1. 가장먼저 누가 누구를 구독하는지 알아야함 (구독 취소도 마찬가지)
	 * 2. 누가 : 로그인한 주체 (principal) / 누구를 : toUserId를
	 * 
	 * */
	
	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
										@PathVariable int toUserId){
		
		subscribeService.doSubscribe(principalDetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1,"구독하기 성공", null), HttpStatus.OK);
	}
	
	@DeleteMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> unsubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails,
											@PathVariable int toUserId){
		 subscribeService.doUnsubscribe(principalDetails.getUser().getId(), toUserId);

		return new ResponseEntity<>(new CMRespDto<>(1,"구독취소하기 성공", null), HttpStatus.OK);

	}
}
