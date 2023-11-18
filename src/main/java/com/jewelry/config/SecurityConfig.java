package com.jewelry.config;

import com.jewelry.config.filter.JwtAuthenticationFilter;
import com.jewelry.config.handler.CustomAccessDeniedHandler;
import com.jewelry.config.handler.CustomAuthenticationEntryPoint;
import com.jewelry.config.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true) //Controller에 @Secured("권한 이름") 어노테이션으로 설정이 가능하다.
@RequiredArgsConstructor
public class SecurityConfig {
	
	//JWT 토큰 제공 Provider
	private final JwtTokenProvider jwtTokenProvider;
    
	// 인증 실패 또는 인증헤더가 전달받지 못했을때 핸들러
    private final CustomAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    // 인가 실패 핸들러
    private final CustomAccessDeniedHandler accessDeniedHandler;
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
        			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 리소스들

//                    .antMatchers(
//            			"/signin", "/access-denied", "/api/jauth/login",
//            			"/css/**", "/html/**", "/img/**", "/js/**", "/scss/**", "/vendor/**", "/plugin/**", "/erd/**",
//            			"/**.js",  "/**.json"
//            		);
	}
    
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        		//JWT 설정
        		.csrf().disable()
        		.httpBasic().disable()
                .formLogin().disable()
							.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //예외처리
	            .and()
	                .exceptionHandling()
	                .authenticationEntryPoint(jwtAuthenticationEntryPoint)	//인증실패
	                .accessDeniedHandler(accessDeniedHandler)	//인가실패
                
                .and()
	                .authorizeRequests()	//인증 필요 설정
		            .antMatchers(
		        			"/signin", "/error", "/error/access-denied", "/api/jauth/login", "/**",
		        			"/css/**", "/html/**", "/img/**", "/js/**", "/scss/**", "/vendor/**", "/plugin/**", "/erd/**",
		        			"/**.js",  "/**.json"
					).permitAll() // 로그인 권한은 누구나, resources파일도 모든권한

	            .antMatchers("/api/**").hasAnyRole("ADMIN", "MANAGER")
	            //.anyRequest().permitAll() //설정한 URI외 모든 접근을 허용한다.
	            
	            .and()
                .headers()
                .frameOptions().sameOrigin();

        return http.build();
    }
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
