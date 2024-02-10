package com.onnoff.onnoff.auth.feignClient.dto.kakao;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Builder
@AllArgsConstructor
public class UnlinkRequest {
    private String targetIdType;
    private String targetId;

    public MultiValueMap<String, String> toUrlEncoded(){
        LinkedMultiValueMap<String, String> urlEncoded = new LinkedMultiValueMap<>();
        urlEncoded.add("target_id_type", targetIdType);
        urlEncoded.add("target_id", targetId);
        return urlEncoded;
    }
}
