package com.onnoff.onnoff.auth.service.tokenValidator;

import com.onnoff.onnoff.domain.user.enums.SocialType;

/**
 * 토큰 검증을 처리하는 역할
 */
public interface SocialTokenValidator {
    void validate(String token, SocialType socialType);
}
