package com.wangyousong.selfstudy.springsecurity.extraloginfieldscustom;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_FORM_DOMAIN_KEY = "domain";
    public static final String POST = "POST";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (!POST.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: "
                    + request.getMethod());
        }

        CustomAuthenticationToken authRequest = getAuthRequest(request);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private CustomAuthenticationToken getAuthRequest(HttpServletRequest request) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String domain = obtainDomain(request);

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        if (domain == null) {
            domain = "";
        }

        return new CustomAuthenticationToken(username, password, domain);
    }

    private String obtainDomain(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_DOMAIN_KEY);
    }
}
