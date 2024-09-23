package com.study.crmProject.common.authority;

import lombok.Data;

@Data
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private Long userId;

    public TokenInfo(String grantType, String accessToken, Long userId) {
        this.grantType      = grantType;
        this.accessToken    = accessToken;
        this.userId         = userId;
    }
}
