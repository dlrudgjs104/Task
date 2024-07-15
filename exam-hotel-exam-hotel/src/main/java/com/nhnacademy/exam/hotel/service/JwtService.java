package com.nhnacademy.exam.hotel.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtService {
	@Value("${jwt.secret}")
	private String signatureSecretKey;

	public SecretKey getSignatureSecretKey() {
		return Keys.hmacShaKeyFor(signatureSecretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String createJWT(Long memberId) {
		return Jwts.builder()
			// 페이로드 정보
			.claim("memberId", memberId)
			// 발행 일
			.setIssuedAt((new Date()))
			// 하루 동안 유효
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24L))

			// 서명키로 서명
			.signWith(getSignatureSecretKey())
			.compact();
	}

	public String getJWT() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
			.getRequest();
		String token = request.getHeader("Authorization");

		log.info("token = {}", token);

		return token;
	}

	public Long getMemberId() {
		String accessToken = getJWT();

		//  예외처리
		if (accessToken == null) {
			return null;
		}
		if (accessToken.isEmpty()) {
			return null;
		}

		Jws<Claims> jws;

		try {
			jws = Jwts.parserBuilder()
				.setSigningKey(getSignatureSecretKey())
				.build()
				.parseClaimsJws(accessToken);
		} catch (JwtException e) {
			throw new RuntimeException("유효하지 않은 JWT 토큰", e);
		}

		// memberId 로 걸었던 클레임을 가져온다.
		return jws.getBody().get("memberId", Long.class);
	}
}
