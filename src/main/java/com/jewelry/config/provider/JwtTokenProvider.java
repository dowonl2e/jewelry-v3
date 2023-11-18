package com.jewelry.config.provider;

import com.amazonaws.util.CollectionUtils;
import com.jewelry.authentication.jwt.entity.TokenVO;
import com.jewelry.config.redis.service.RedisService;
import com.jewelry.exception.CustomException;
import com.jewelry.response.ResponseCode;
import com.jewelry.values.JwtHeader;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional(readOnly = true)
public class JwtTokenProvider {

	private final long accessTokenValidityInMilliseconds;

	private final long refreshTokenValidityInMilliseconds;

	private final RedisService redisService;

	private Key key;

    
	public JwtTokenProvider(
			RedisService redisService,
			@Value("${jwt.secret}") String secretKey,
			@Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidityInMilliseconds,
			@Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenValidityInMilliseconds){
		this.redisService = redisService;
		byte[] keyBytes = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds * 1000;
		this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
	}

	/**
	 * token 생성 algorithm
	 *
	 */
	public TokenVO authentication(Authentication authentication){
		//권한 가져오기
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
		
		Date now = new Date();
		
		Date accessTokenExpioresIn = new Date(now.getTime() + accessTokenValidityInMilliseconds);
		//AccessToken 생성
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName()) 		// payload "sub": "name"
				.setIssuedAt(now)
				.claim(JwtHeader.AUTHORITY_TYPE_HEADER.getValue(), authorities)		// payload "Authorization": "ROLE_ADMIN" OR "ROLE_MANAGER"
				.setExpiration(accessTokenExpioresIn)		// payload "exp": 1516239022 (예시)
				.signWith(key, SignatureAlgorithm.HS512)	// header "alg": "HS512"
				.compact();
		
		
		Date refreshTokeExpioresIn = new Date(now.getTime() + refreshTokenValidityInMilliseconds);
		String refreshToken = Jwts.builder()
				.setExpiration(refreshTokeExpioresIn)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
		
		return TokenVO.builder()
				.grantType(JwtHeader.GRANT_TYPE_PREFIX.getValue())
				.accessToken(accessToken)
				.accessTokenExpioresIn(accessTokenExpioresIn.getTime())
				.refreshToken(refreshToken)
				.refreshTokeExpioresIn(refreshTokeExpioresIn.getTime())
				.build();
	}
    
	/**
	 * token 생성 algorithm
	 *
	 */
	public TokenVO generateToken(UserDetails userDetails){
		//권한 가져오기
		String authorities = userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
		
		Date now = new Date();
		
		Date accessTokenExpioresIn = new Date(now.getTime() + accessTokenValidityInMilliseconds);
		//AccessToken 생성
		String accessToken = Jwts.builder()
				.setSubject(userDetails.getUsername()) 		// payload "sub": "name"
				.setIssuedAt(now)
				.claim(JwtHeader.AUTHORITY_TYPE_HEADER.getValue(), authorities)		// payload "Authorization": "ROLE_ADMIN" OR "ROLE_MANAGER"
				.setExpiration(accessTokenExpioresIn)		// payload "exp": 1516239022 (예시)
				.signWith(key, SignatureAlgorithm.HS512)	// header "alg": "HS512"
				.compact();
		
		
		Date refreshTokeExpioresIn = new Date(now.getTime() + refreshTokenValidityInMilliseconds);
		String refreshToken = Jwts.builder()
				.setExpiration(refreshTokeExpioresIn)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
		
		return TokenVO.builder()
				.grantType(JwtHeader.GRANT_TYPE_PREFIX.getValue())
				.accessToken(accessToken)
				.accessTokenExpioresIn(accessTokenExpioresIn.getTime())
				.refreshToken(refreshToken)
				.refreshTokeExpioresIn(refreshTokeExpioresIn.getTime())
				.build();
	}

	public TokenVO generateToken(String principal, String authorities){
		Date now = new Date();

		Date accessTokenExpiresIn = new Date(now.getTime() + accessTokenValidityInMilliseconds);
		//AccessToken 생성
		String accessToken = Jwts.builder()
				.setSubject(principal) 		// payload "sub": "name"
				.setIssuedAt(now)
				.claim(JwtHeader.AUTHORITY_TYPE_HEADER.getValue(), authorities)		// payload "Authorization": "ROLE_ADMIN" OR "ROLE_MANAGER"
				.setExpiration(accessTokenExpiresIn)		// payload "exp": 1516239022 (예시)
				.signWith(key, SignatureAlgorithm.HS512)	// header "alg": "HS512"
				.compact();


		Date refreshTokeExpioresIn = new Date(now.getTime() + refreshTokenValidityInMilliseconds);
		String refreshToken = Jwts.builder()
				.setExpiration(refreshTokeExpioresIn)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();

		return TokenVO.builder()
				.grantType(JwtHeader.GRANT_TYPE_PREFIX.getValue())
				.accessToken(accessToken)
				.accessTokenExpioresIn(accessTokenExpiresIn.getTime())
				.refreshToken(refreshToken)
				.refreshTokeExpioresIn(refreshTokeExpioresIn.getTime())
				.build();
	}

	/**
	 * 토큰을 복호화하여 토큰에 들어있는 정보를 가져온다.
	 * AccessToken에만 유저 정보를 담고 있다.
	 * SecurityContext가 Authentication를 지니고 있기에 UserDetails는 SecurityContext를 사용하기 위함이다.
	 * @param accessToken
	 * @return
	 */
	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);
		
		if(claims.get(JwtHeader.AUTHORITY_TYPE_HEADER.getValue()) == null) {
			throw new CustomException(ResponseCode.NOT_EXIST_AUTH_TOKEN);
		}

		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get(JwtHeader.AUTHORITY_TYPE_HEADER.getValue()).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
		
	}

    /**
	 * 토큰을 복호화 한다.
	 * @param accessToken
	 * @return
	 */
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	/**
	 * 복호화된 토큰에서 name 추출한다.
	 * @param accessToken
	 * @return
	 */
	public String getSubject(String accessToken) {
		return parseClaims(accessToken).getSubject();
	}
    
	/**
	 * 복호화된 토큰에서 role 추출한다.
	 * @param accessToken
	 * @return
	 */
	public String getAuthorities(String accessToken) {
		Collection<? extends GrantedAuthority> authorities = getAuthentication(accessToken).getAuthorities();
		boolean isAdmin = authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent();
		if(isAdmin)
			return "ADMIN";
		else {
			List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
			return CollectionUtils.isNullOrEmpty(roles) ? "NONE" : roles.get(0);
		}
	}

	/**
	 * 인증객체에서 권한 가져오기
	 * @param authentication
	 * @return
	 */
	public String getAuthorities(Authentication authentication) {
    return authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));
  }

	public long getTokenExpirationTime(String token) {
		return parseClaims(token).getExpiration().getTime();
	}

	/**RefreshToken 유효성 검증 */
	public boolean validateRefreshToken(String refreshToken){
		try{
			if (redisService.getValues(refreshToken) != null // NPE 방지
					&& redisService.getValues(refreshToken).equals("logout")) { // 로그아웃 했을 경우
				return false;
			}

			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
			return true;
		}
		catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
			log.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.info("지원하지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.info("JWT 토큰이 잘못되었습니다.");
		} catch (NullPointerException e){
			log.info("JWT 토큰이 없습니다.");
		}
		return false;
	}

	/**token 유효성 검증 */
	public boolean validateToken(String accessToken){
		try{
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
			return true;
		}catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
			log.info("잘못된 JWT 서명입니다.");
		}catch(ExpiredJwtException e){
			log.info("만료된 JWT 토큰입니다.");
		}catch(UnsupportedJwtException e){
			log.info("지원하지 않는 JWT 토큰입니다.");
		}catch(IllegalArgumentException e){
			log.info("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}

	// 재발급 검증 API에서 사용
	public boolean validateAccessTokenIsExpired(String accessToken) {
		try {
			return parseClaims(accessToken)
					.getExpiration()
					.before(new Date());
		} catch(ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
			return true;
		} catch (Exception e) {
			log.info("JWT 토큰이 잘못되었습니다.");
			return false;
		}
	}

	//AccessToken에서 principal(userId) 추출하기
	public String getPrincipal(String accessToken){
		return getAuthentication(accessToken).getName();
	}

	// "Bearer {accessToken}"에서 {accessToken} 추출
	public String resolveToken(String accessToken){
		if (accessToken != null && accessToken.startsWith("Bearer ")) {
			return accessToken.substring(7);
		}
		return null;
	}

}
