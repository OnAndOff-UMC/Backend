package com.onnoff.onnoff.domain.user.enums;

public enum SocialType {
    KAKAO, APPLE;

    public static boolean isApple(SocialType socialType){
        return socialType.equals(SocialType.APPLE);
    }
}
