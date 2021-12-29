package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	@Transactional
	public User updateUser(int id, User user) {
		
		//1. 영속화
		/*
		 * [findById()에 .get()붙여주는 이유]
		 * 1. findById 같은 무언가를 찾아주는 메소드는 리턴타입이 Optional임
		 * 2. 그 이유는 못찾을 경우 null을 리턴해야 하기 때문임
		 * 3. 따라서, Optional에서 제공하는 대표 메소드가 2가지 (get(), orElseThrow()) 가 존재함
		 * 4. get() : 무조건 찾는다고 설정하여 예외발생 시키지 않기 위함 (테스트시 사용예정)
		 * 5. orElseThrow() : null값인 경우 예외를 발생하게 됨 (추후에 테스트 완료후 해당 메서드로 바꿔야함)
		 * 
		 * 6. 유효성 검증을 위해 get()을 orElseThrow()로 변경
		 * 7. 해당 메서드는 Supplier 타입을 받으며, 잘못된 매개변수 예외인 IllegalArgumentException을 발생시키도록 설정
		 * 		 * */
//		User userEntity = userRepository.findById(10).orElseThrow(new Supplier<IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				// TODO Auto-generated method stub
//				return new IllegalArgumentException("찾을수 없는 아이디입니다.");
//			}
//		});
		//상기 코드를 람다식으로 가독성 좋게 변경
		//또한 만들어둔 CustomValidationApiException에서 잡을 수 있도록 예외 클래스 변경
		//변경하면 컴파일에러가 발생하는데, 생성자가 유효한 것이 없기 때문임. 스트링만 받는 생성자 하나 추가하도록 하자
		User userEntity = userRepository.findById(10).orElseThrow(()-> {
			return new CustomValidationApiException("찾을수 없는 아이디입니다.");
				});
		
		userEntity.setName(user.getName());
		
		
		userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		//2. 영속화된 오브젝트를 수정 - 더티체킹 (업데이트완료)
		return userEntity;
	} //더티체킹이 일어나서 업데이트가 완료됨
}
