package com.study.crmProject.user.contriller;

import com.study.crmProject.user.entity.User;
import lombok.Data;

@Data
public class UserInfoResponse {
    private String loginId;
    private String name;
    private String email;

    public UserInfoResponse(User user) {
        this.loginId    = user.getLoginId();
        this.name       = user.getName();
        this.email      = user.getEmail();
    }
}
