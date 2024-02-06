package com.onnoff.onnoff.auth.service;


import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.auth.feignClient.client.AppleAuthClient;
import com.onnoff.onnoff.auth.feignClient.dto.apple.TokenRequest;
import com.onnoff.onnoff.auth.feignClient.dto.TokenResponse;
import com.onnoff.onnoff.auth.service.tokenValidator.SocialTokenValidator;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.enums.SocialType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppleLoginService implements LoginService{
    private final AppleAuthClient appleAuthClient;
    private final SocialTokenValidator validator;
    @Value("${apple.key.id}")
    private String kid;
    @Value("${apple.key.path}")
    private String keyPath;
    @Value("${apple.client-id}")
    private String clientId;
    @Value("${apple.iss}")
    private String iss;
    @Value("${apple.team-id}")
    private String teamId;
    @Override
    public TokenResponse getAccessTokenByCode(String code) {
        // client secret 만들기
        String clientSecret = createClientSecret();
        // 요청
        MultiValueMap<String, String> urlEncoded = TokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .grantType("authorization_code")
                .build().toUrlEncoded();
        return appleAuthClient.getToken(urlEncoded);
    }
    private String createClientSecret() {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("kid", kid);
        jwtHeader.put("alg", "ES256");

        try {
            return Jwts.builder()
                    .setHeaderParams(jwtHeader)
                    .setIssuer(teamId) // 토큰 발행자 = 우리 팀
                    .setIssuedAt(new Date(System.currentTimeMillis())) // 발행 시간 - UNIX 시간
                    .setExpiration(expirationDate) // 만료 시간
                    .setAudience(iss)  // 애플이 수신자
                    .setSubject(clientId) // 토큰의 주체 = 우리 앱
                    .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                    .compact();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getAccessTokenByRfToken(String code) {
        // client secret 만들기
        String clientSecret = createClientSecret();
        // refreshToken 가져오기
        User user = UserContext.getUser();
        String appleRefreshToken = user.getAppleRefreshToken();
        // 요청
        MultiValueMap<String, String> urlEncoded = TokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .refreshToken(appleRefreshToken)
                .grantType("refresh_token")
                .build().toUrlEncoded();
        TokenResponse response = appleAuthClient.getToken(urlEncoded);
        return null;
    }
    private PrivateKey getPrivateKey() throws IOException {
        ClassPathResource resource = new ClassPathResource(keyPath);
        String privateKey = new String(Files.readAllBytes(Paths.get(resource.getURI())));

        Reader pemReader = new StringReader(privateKey);
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        return converter.getPrivateKey(object);
    }


    @Override
    public void validate(String identityToken){
        String cleanedIdentityToken = cleanToken(identityToken);
        validator.validate(cleanedIdentityToken, SocialType.APPLE);
    }
}
