package com.onnoff.onnoff.auth.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}
