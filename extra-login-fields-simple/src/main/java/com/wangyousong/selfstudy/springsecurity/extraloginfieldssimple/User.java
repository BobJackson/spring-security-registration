package com.wangyousong.selfstudy.springsecurity.extraloginfieldssimple;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Administrator
 */
public class User extends org.springframework.security.core.userdetails.User {

    private final String domain;

    public User(String username, String domain, String password, boolean enabled,
                boolean accountNonExpired, boolean credentialsNonExpired,
                boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
