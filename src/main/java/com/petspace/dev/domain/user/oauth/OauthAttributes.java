package com.petspace.dev.domain.user.oauth;

import com.petspace.dev.domain.user.Role;
import com.petspace.dev.domain.Status;
import com.petspace.dev.domain.user.User;

import java.util.Arrays;
import java.util.Map;

public enum OauthAttributes {
    KAKAO("kakao") {
        public User of(Map<String, Object> attributes) {
            KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(attributes);
            return User.builder()
                    .nickname(kakaoUserInfo.getNickName())
                    .email(kakaoUserInfo.getEmail())
                    .oauthProvider(kakaoUserInfo.getProvider())
                    .privacyAgreement(true)
                    .hostPermission(false)
                    .status(Status.ACTIVE)
                    .role(Role.USER)
                    .build();
        }
    };

    private final String providerName;

    OauthAttributes(String providerName) {
        this.providerName = providerName;
    }

    public static User extract(String providerName, Map<String, Object> userAttributes) {
        return Arrays.stream(values())
                .filter(provider -> providerName.equals(provider.providerName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of(userAttributes);
    }

    public abstract User of(Map<String, Object> attributes);
}