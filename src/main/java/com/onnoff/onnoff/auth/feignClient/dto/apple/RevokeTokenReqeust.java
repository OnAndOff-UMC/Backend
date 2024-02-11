package com.onnoff.onnoff.auth.feignClient.dto.apple;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Builder
@AllArgsConstructor
public class RevokeTokenReqeust {
    private String clientId;
    private String clientSecret;
    private String token;
    private String tokenTypeHint;

    public MultiValueMap<String, String> toUrlEncoded(){
        LinkedMultiValueMap<String, String> urlEncoded = new LinkedMultiValueMap<>();
        urlEncoded.add("client_id", clientId);
        urlEncoded.add("client_secret", clientSecret);
        urlEncoded.add("token", token);
        urlEncoded.add("token_type_hint", "refresh_token");
        return urlEncoded;
    }
}
