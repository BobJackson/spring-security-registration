package com.wangyousong.selfstudy.springsecurity.extraloginfieldscustom;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Administrator
 */
public interface CustomUserDetailsService {

    /**
     * load user by username and domain
     *
     * @param username username
     * @param domain   domain
     * @return UserDetails
     * @throws UsernameNotFoundException UsernameNotFoundException
     */
    UserDetails loadUserByUsernameAndDomain(String username, String domain) throws UsernameNotFoundException;

}
