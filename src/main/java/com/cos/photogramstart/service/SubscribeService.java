package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;

	@Transactional
	public void doSubscribe(int fromUserId, int toUserId) {
		//JpaRepository의 save메소드 사용시, int 값을 User로 바꾸기 까다롭기 때문에 네이티브 쿼리가 더나을 수 있음
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch(Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public void doUnsubscribe(int fromUserId, int toUserId) {
		
		 subscribeRepository.mUnsubscribe(fromUserId, toUserId);
	}
}
