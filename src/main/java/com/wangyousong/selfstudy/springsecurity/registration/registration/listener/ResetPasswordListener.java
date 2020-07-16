package com.wangyousong.selfstudy.springsecurity.registration.registration.listener;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import com.wangyousong.selfstudy.springsecurity.registration.registration.OnResetPasswordEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/16 18:38
 */
@Component
@Slf4j
public class ResetPasswordListener implements ApplicationListener<OnResetPasswordEvent> {

    @Resource
    private MessageSource messages;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private Environment env;


    @Override
    public void onApplicationEvent(OnResetPasswordEvent event) {
        final SimpleMailMessage email = constructResetTokenEmail(event.getAppUrl(), event.getLocale(),
                event.getToken(), event.getUser());
        mailSender.send(email);
        log.info("send reset password email");
    }


    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale,
                                                       final String token, final User user) {
        final String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Reset Password");
        email.setText(message + " \r\n" + url);
        email.setFrom(Objects.requireNonNull(env.getProperty("support.email")));
        return email;
    }
}
