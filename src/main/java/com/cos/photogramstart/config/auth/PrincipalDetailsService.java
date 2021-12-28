package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	//1. 패스워드는 알아서 체킹해서 신경 쓸 필요가 없음
	//2. 리턴이 정상적으로 이루어지면 자동으로 UserDetails 타입을 세션으로 만듦
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) {
			return null;
		}else {
			//PrincipalDetails를 따로 구현한 이유는 loadByUsername의 리턴 타입이 UserDetails이기 때문
			//따라서 UserDetails를 구현한 PrincipalDetails를 리턴하므로 로직이 이상이 없는 것이며,
			//PrincipalDetails에는 회원정보를 가지고 있는 userEntity를 넣어줌으로써,
			//PrincipalDetails 자체도 User의 정보를 갖게 만드는 것임
			return new PrincipalDetails(userEntity);
		}
	}
}
