package com.wangyousong.selfstudy.springsecurity.registration.registration.listener;


import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import com.wangyousong.selfstudy.springsecurity.registration.registration.OnRegistrationCompleteEvent;
import com.wangyousong.selfstudy.springsecurity.registration.service.IUserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/15 14:56
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final IUserService service;

    @Resource
    private MessageSource messages;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private Environment env;

    public RegistrationListener(IUserService service) {
        this.service = service;
    }

    // API

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    //

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user,
                                                    final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        final String message = messages.getMessage("message.regSuccLink",
                null,
                "You registered successfully. To confirm your registration, please click on the below link.",
                event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(Objects.requireNonNull(env.getProperty("support.email")));
        return email;
    }


}
