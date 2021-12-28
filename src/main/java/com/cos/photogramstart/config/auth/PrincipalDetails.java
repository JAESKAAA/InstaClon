package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private static final long serialVersionUID = 1L;

	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//여기가 컬렉션인 이유는 ?
	//하나의 유저가 가진 권한이 하나가 아닐수 있기 때문임
	//리턴타입이 Collection이므로 Collection을 따로 정의해줘야함
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collector = new ArrayList<>();
		//collector의 타입이 GrantedAuthority만 가능하므로 해당 객체를 익명객체로 만들어 메소드 오버라이드 함
//		collector.add(new GrantedAuthority() {
//			
//			@Override
//			//GrantedAuthority의 getAuthority는 반환타입이 String이므로 user의 권한을 바로 넣어줄 수 있음
//			public String getAuthority() {
//				return user.getRole();
//			}
//		});
		//상기 코드를 람다식으로 변환
		collector.add(()->{return user.getRole();});
		
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
