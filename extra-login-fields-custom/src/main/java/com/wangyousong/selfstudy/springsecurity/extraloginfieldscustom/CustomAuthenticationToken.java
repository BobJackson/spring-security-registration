package com.wangyousong.selfstudy.springsecurity.extraloginfieldscustom;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Administrator
 */
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String domain;

    public CustomAuthenticationToken(Object principal, Object credentials, String domain) {
        super(principal, credentials);
        this.domain = domain;
        super.setAuthenticated(false);
    }

    public CustomAuthenticationToken(Object principal, Object credentials, String domain,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.domain = domain;
        // must use super, as we override
        super.setAuthenticated(true);
    }

    public String getDomain() {
        return this.domain;
    }
}
