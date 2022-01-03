package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		// file:///C:/Dev211/SpringBoot/instaclon/upload/ 의 형태로 변환해줌
		registry
			.addResourceHandler("/upload/**") //jsp페이지에서 /upload/**와 같은 패턴이 나오면 발동됨
			.addResourceLocations("file:///"+uploadFolder) //상기 패턴이 발동되면 해당 메소드가 발동됨
			.setCachePeriod(60*10*6) //초단위이며, 유효기간을 설정 하는 것 (1시간)
			.resourceChain(true)
			.addResolver(new PathResourceResolver());
	}
}
