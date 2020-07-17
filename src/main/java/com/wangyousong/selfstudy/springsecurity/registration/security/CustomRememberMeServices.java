package com.wangyousong.selfstudy.springsecurity.registration.security;


import com.wangyousong.selfstudy.springsecurity.registration.persistence.dao.UserRepository;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

/**
 * @author Administrator
 */
public class CustomRememberMeServices extends PersistentTokenBasedRememberMeServices {

    @Resource
    private UserRepository userRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource =
            new WebAuthenticationDetailsSource();
    private final PersistentTokenRepository tokenRepository;
    private final String key;

    public CustomRememberMeServices(String key, UserDetailsService userDetailsService,
                                    PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
        this.tokenRepository = tokenRepository;
        this.key = key;
    }

    @Override
    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
                                  Authentication successfulAuthentication) {
        String username;
        if (successfulAuthentication.getPrincipal() instanceof User) {
            username = ((User) successfulAuthentication.getPrincipal()).getEmail();
        } else {
            username = successfulAuthentication.getName();
        }
        logger.debug("Creating new persistent login for user " + username);
        PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(username, generateSeriesData(),
                generateTokenData(), new Date());
        try {
            tokenRepository.createNewToken(persistentToken);
            addCookie(persistentToken, request, response);
        } catch (Exception e) {
            logger.error("Failed to save persistent token ", e);
        }
    }

    @Override
    protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
        Optional<User> auser = userRepository.findByEmail(user.getUsername());
        assert auser.orElse(null) != null;
        RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(key, auser.orElse(null),
                authoritiesMapper.mapAuthorities(user.getAuthorities()));
        auth.setDetails(authenticationDetailsSource.buildDetails(request));
        return auth;
    }

    private void addCookie(PersistentRememberMeToken token, HttpServletRequest request, HttpServletResponse response) {
        setCookie(new String[]{token.getSeries(), token.getTokenValue()}, getTokenValiditySeconds(), request, response);
    }
}
