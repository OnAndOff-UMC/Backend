package com.onnoff.onnoff.auth.jwt.service;

import com.onnoff.onnoff.auth.jwt.dto.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
   토큰의 속성을 정의하고 생성하는 역할
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtil {
    @Value("${spring.jwt.secret}")
    private String secret;
    private SecretKey secretkey;
    @PostConstruct
    public void init() {
        this.secretkey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }


    public JwtToken generateToken(String id) {
        // refreshToken과 accessToken을 생성한다.
        String refreshToken = generateRefreshToken(id);
        String accessToken = generateAccessToken(id);
        log.info("accessToken = {}", accessToken);
        return new JwtToken(accessToken, refreshToken);
    }

    public String generateRefreshToken(String id) {
        // 토큰의 유효 기간을 밀리초 단위로 설정.
        long refreshPeriod = 1000L * 60L * 60L * 24L * 14; // 2주

        // 새로운 클레임 객체를 생성하고, 아이디를 셋
        Claims claims = Jwts.claims()
                .subject(id)
                .build();
        // 현재 시간과 날짜를 가져온다.
        Date now = new Date();

        return Jwts.builder()
                // Payload를 구성하는 속성들을 정의한다.
                .setClaims(claims)
                // 발행일자를 넣는다.
                .setIssuedAt(now)
                // 토큰의 만료일시를 설정한다.
                .setExpiration(new Date(now.getTime() + refreshPeriod))
                // 지정된 서명 알고리즘과 비밀 키를 사용하여 토큰을 서명한다.
                .signWith(secretkey, SignatureAlgorithm.HS256)
                // JWT의 각 부분을 Base64Url 인코딩하고 이들을 '.'으로 연결하여 최종적인 JWT 문자열을 생성합니다.(URL-safe 문자열로 압축)
                .compact();
    }


    public String generateAccessToken(String id) {

        long tokenPeriod = 1000L * 60L * 60L; // 60분
        Claims claims = Jwts.claims()
                .subject(id)
                .build();

        Date now = new Date();
        return
                Jwts.builder()
                        // Payload를 구성하는 속성들을 정의한다.
                        .setClaims(claims)
                        // 발행일자를 넣는다.
                        .setIssuedAt(now)
                        // 토큰의 만료일시를 설정한다.
                        .setExpiration(new Date(now.getTime() + tokenPeriod))
                        // 지정된 서명 알고리즘과 비밀 키를 사용하여 토큰을 서명한다.
                        .signWith(secretkey, SignatureAlgorithm.HS256)
                        .compact();
    }

    // 토큰에서 유저 id를추출한다.
    public String getUserId(String token) {
        return Jwts.parser()
                .verifyWith(secretkey)
                .build() // 비밀키를 설정하여 파서를 빌드.
                .parseSignedClaims(token)// 주어진 토큰을 파싱하여 Claims 객체를 얻는다.
                .getPayload()
                .getSubject();
    }
}