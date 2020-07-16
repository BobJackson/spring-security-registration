package com.wangyousong.selfstudy.springsecurity.registration.registration;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/15 14:56
 */
@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final String appUrl;
    private final Locale locale;
    private final User user;
    private final String token;

    public OnRegistrationCompleteEvent(Object source, String appUrl, Locale locale, User user) {
        super(source);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
        this.token = null;
    }

    public OnRegistrationCompleteEvent(Object source, String appUrl, Locale locale, User user, String token) {
        super(source);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
        this.token = token;
    }
}
