package com.wangyousong.selfstudy.springsecurity.registration.security;

import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Administrator
 */
@Component("authenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public static final String USER_IS_DISABLED = "User is disabled";
    public static final String USER_ACCOUNT_HAS_EXPIRED = "User account has expired";
    public static final String BLOCKED = "blocked";
    public static final String UNUSUAL_LOCATION = "unusual location";

    @Resource
    private MessageSource messages;

    @Resource
    private LocaleResolver localeResolver;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error=true");

        super.onAuthenticationFailure(request, response, exception);

        final Locale locale = localeResolver.resolveLocale(request);

        String errorMessage = messages.getMessage("message.badCredentials", null, locale);

        if (USER_IS_DISABLED
                .equalsIgnoreCase(exception.getMessage())) {
            errorMessage = messages.getMessage("auth.message.disabled", null, locale);
        } else if (USER_ACCOUNT_HAS_EXPIRED
                .equalsIgnoreCase(exception.getMessage())) {
            errorMessage = messages.getMessage("auth.message.expired", null, locale);
        } else if (BLOCKED
                .equalsIgnoreCase(exception.getMessage())) {
            errorMessage = messages.getMessage("auth.message.blocked", null, locale);
        } else if (UNUSUAL_LOCATION
                .equalsIgnoreCase(exception.getMessage())) {
            errorMessage = messages.getMessage("auth.message.unusual.location", null, locale);
        }

        request.getSession()
                .setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}