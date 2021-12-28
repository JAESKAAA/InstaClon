package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //1. Ioc 2. 트랜잭션 관리
public class AuthService {


	
	//DB 커넥션을 위한 repository 의존성 주입
	private final UserRepository userRepository;
	//패스워드 암호화를 위한 인코더 주입
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	//회원가입할때 DB에 값이 들어가려면 Repository가 필요함.
	//따라서 domain.user 패키지에 repository 생성필요
	@Transactional //해당 어노테이션을 붙이면 메소드 실행~종료까지 트랜잭션 관리를 해줌. write(insert, update, delete)시 필요
	public User signUp(User user) {
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		user.setPassword(encPassword);
		user.setRole("ROLE_USER"); //관리자는 ROLE_ADMIN 예정
		
		//회원가입 진행
		//save는 저장된 entity를 반환해줌. 이말은 매개변수로 받은 user와는 다른 객체라는 의미이기도 함
		User userEntity = userRepository.save(user);
		return userEntity;
	}
}
