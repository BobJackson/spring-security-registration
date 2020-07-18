package com.wangyousong.selfstudy.springsecurity.registration.security;


import com.wangyousong.selfstudy.springsecurity.registration.persistence.dao.UserRepository;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.Privilege;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.Role;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
@Service("userDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private HttpServletRequest request;

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    public MyUserDetailsServiceImpl(UserRepository userRepository, LoginAttemptService loginAttemptService) {
        super();
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
    }

    // API

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        final String ip = getClientIp();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }

        try {
            final Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                throw new UsernameNotFoundException("No user found with username: " + email);
            }
            User user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    // UTIL

    private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<>();
        final List<Privilege> collection = new ArrayList<>();
        for (final Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }

        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private String getClientIp() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }


}
