package com.onnoff.onnoff.auth.service;


import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.auth.feignClient.client.AppleAuthClient;
import com.onnoff.onnoff.auth.feignClient.dto.apple.TokenRequest;
import com.onnoff.onnoff.auth.feignClient.dto.TokenResponse;
import com.onnoff.onnoff.auth.service.tokenValidator.SocialTokenValidator;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.enums.SocialType;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
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
        log.info("clientSecret = {}", clientSecret);
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

        try {
            log.info("teamId = {}", teamId);
            log.info("kid = {}", kid);
            return Jwts.builder()
                    .header()
                    .keyId(kid)
                    .add("alg", "ES256")
                    .and()
                    .subject(clientId) // 토큰의 주체 = 우리 앱
                    .issuer(teamId)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(expirationDate) // 만료 시간
                    .audience()
                    .add(iss)
                    .and()
                    .signWith(getPrivateKey(), Jwts.SIG.ES256)
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
        String privateKey = new String(Files.readAllBytes(Paths.get(keyPath) ) );
        log.info("privateKey = {}", privateKey);
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
