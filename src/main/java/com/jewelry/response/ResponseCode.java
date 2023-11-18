package com.jewelry.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ResponseCode {
	
	//회원 관련 에러
	//패턴
	//HttpStatus.code + Method(0:GET / 1:POST/ 2:PATCH / 3:DELETE) + 사용자별 패턴
	
	/* 회원 관련 성공코드
	 * 패턴 : 00 
	 */
	SUCCESS(HttpStatus.OK, "200000", "정상처리되었습니다."),
	
	AUTH_SUCCESS(HttpStatus.OK, "200401", "인증되었습니다."),

	AUTH_FAIL(HttpStatus.UNAUTHORIZED, "400401", "인증에 실패했습니다."),
	
	TOKEN_CREATE_SUCCESS(HttpStatus.OK, "200100", "토큰이 생성되었습니다."),
	
	USER_FIND_SUCCESS(HttpStatus.OK, "200200", "회원 정보 정상 조회되었습니다."),
		
	LOGOUT_USER(HttpStatus.BAD_REQUEST, "404201", "로그아웃된 사용자입니다."),
	
	DATA_PROCESS_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "500000", "데이터 처리에 문제가 발생하였습니다."),
    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500000", "오류가 발생하였습니다."),

	/*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400000", "잘못된 요청입니다."),

    /*
     * 401 UNAUTHORIZED: 권한이 없음
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401000", "인증 권한이 없습니다."),
    
    /*
     * 403 FORBIDDRN: 권한이 없음
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "403000", "권한이 없어 요청이 거부되었습니다."),
    
    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "404000", "게시글 정보를 찾을 수 없습니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "405000", "허용되지 않은 메서드입니다."),
    
    //토큰
  	INVALID_AUTH_TOKEN(HttpStatus.BAD_REQUEST, "404100", "권한 정보가 없는 토큰입니다."),
  	UNSUPPORTED_AUTH_TOKEN(HttpStatus.BAD_REQUEST, "404101", "지원되지 않는 토큰입니다."),
  	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "404102", "유효하지 않는 리프레시 토큰입니다."),
  	NOT_EXIST_USERINFO_TOKEN(HttpStatus.BAD_REQUEST, "404103", "토큰의 유저 정보가 없습니다."),
  	MISMATCH_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "404104", "토큰의 유저 정보가 일치하지 않습니다."),
  	EXPIRED_AUTH_TOKEN(HttpStatus.BAD_REQUEST , "404105", "만료된 토큰입니다."),
  	NOT_EXIST_AUTH_TOKEN(HttpStatus.BAD_REQUEST, "404106", "토큰 정보가 없습니다."),
  	WRONG_AUTH_TOKEN(HttpStatus.BAD_REQUEST, "404107", "잘못된 토큰 정보입니다."),
	
	//회원 관련 에러코드
	USER_EXIST_JOIN_FAIL(HttpStatus.BAD_REQUEST, "404201", "이미 가입되어 있는 유저입니다."),
	USER_INVALID(HttpStatus.BAD_REQUEST, "404202", "일치하는 회원 정보가 없습니다."),
	USER_JOIN_FAIL(HttpStatus.BAD_REQUEST, "404203", "회원가입에 실패했습니다."),
	USER_MISMATCH_EMAIL(HttpStatus.BAD_REQUEST, "404204", "일치하는 이메일 정보가 없습니다."),
	USER_MISMATCH_PWD(HttpStatus.BAD_REQUEST, "404205", "비밀번호가 틀렸습니다.");
	
	private HttpStatus status;
	private String code;
	private String message;
}
