package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //해당 파일로 security를 활성화   
@Configuration //IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //커스텀시 해당 어댑터를 상속받아 재정의 해줘야함

	@Bean  //Bean 어노테이션 등록시 SecurityConfig가 빈등록 될때 해당어노테이션을 읽어 return의 생성자를 수행하고,
			//해당 객체 (BCryptPasswordEncoder)를 들고있게 됨
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super삭제 -> 기존 시큐리티가 가지고 있는 기능이 전부 비활성화 됨
		//따라서, 다시 커스텀으로 설정을 잡아줘야함
		
		/*
		 * [csrf 관련]
		 * 1. csrf() : 정상적 접근인지 비정상적인 접근인지를 판단해줌 
		 * 2. 해당 메소드가 활성화 되어있으면 postman을 이용한 테스트가 어렵고, 자바스크립트 사용에서 고려할점이 많아짐
		 * 3. 따라서, 비활성화 후 처리할 예정
		 * 
		 * */
		
		http.csrf().disable();
		
		/*
		 * [하기 코드는 빌더패턴으로 이루어져 있으며, 메소드 내용은 아래와 같음]
		 * 
		 * authorizeRequests() : url패턴을 통해 HttpServletRequest를 기반으로 접근을 제한하겠다는 의미
		 * antMatchers() : url을 매핑해주는 메소드 (/**의 경우 해당 디렉토리의 하위폴더까지 모두 매핑하겠다는 의미)
		 * authenticated() : 인증된 사용자만이 antMatchers에서 매핑된 url을 사용할 수 있도록 설정
		 * anyReqeust() : 어떤 요청이든지를 의미함
		 * permitAll() : 누구에게나 접근을 허용하겠다는 의미
		 * and() : 메서드 체인의 역할을 함 (하나의 설정이 끝나고 다음 설정을 연결해줄때 활용 가능)
		 * formLogin() : form기반 로그인을 지원하도록 설정 (form 태그를 이용한 로그인 방법)
		 * loginPage() : redirect할 로그인 페이지의 url 설정
		 * defaultSuccessUrl() : 로그인 성공시 redirect할 url 설정
		 * 
		 * 
		 * 
		 * 따라서, user, image, subscribe, comment가 url에 포함되어 요청이오면 403(권한 없다는 코드)가 발생하고, 
		 * 해당 url 외의 주소로 요청이오면 anyRequest().permitAll() 되어 있기 때문에 정상 접근 허용된다.
		 * 
		 * */
		
		
		http.authorizeRequests() 
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/auth/signin")
			.defaultSuccessUrl("/");
	}
}
