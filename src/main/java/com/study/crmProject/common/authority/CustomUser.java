package com.study.crmProject.common.authority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

public class CustomUser extends User {
    private Long userId;
    private String userName;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public CustomUser(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
