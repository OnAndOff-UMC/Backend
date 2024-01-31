package com.onnoff.onnoff.auth.jwt.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}
