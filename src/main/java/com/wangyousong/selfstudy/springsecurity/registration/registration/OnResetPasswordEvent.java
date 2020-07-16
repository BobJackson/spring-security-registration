package com.wangyousong.selfstudy.springsecurity.registration.registration;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/16 18:40
 */
@Getter
public class OnResetPasswordEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final User user;
    private final String token;

    public OnResetPasswordEvent(Object source, String appUrl, Locale locale, User user, String token) {
        super(source);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
        this.token = token;
    }

}
