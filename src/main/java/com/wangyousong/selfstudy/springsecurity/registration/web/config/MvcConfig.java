package com.wangyousong.selfstudy.springsecurity.registration.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 19:44
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer{

    private final MessageSource messageSource;

    public MvcConfig(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/successRegister.html");
        registry.addViewController("/login");
        registry.addViewController("/registration.html");
        registry.addViewController("/homepage.html");
        registry.addViewController("/badUser.html");
        registry.addViewController("/emailError.html");
        registry.addViewController("/forgetPassword.html");
        registry.addViewController("/updatePassword.html");
    }

}
