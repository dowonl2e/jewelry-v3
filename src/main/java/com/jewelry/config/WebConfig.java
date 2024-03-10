package com.jewelry.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String[] excludePathPatterns = {"/css/**", 
				"/signin","/access-denied",
				"/error",
				"/html/**",
				"/img/**", 
				"/js/**",
				"/scss/**", 
				"/fonts/**", 
				"/plugin/**", 
				"/vendor/**",
				"/erd/**",
				"/**.js",
				"/**.json",
				"/favicon**"
		};

		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
