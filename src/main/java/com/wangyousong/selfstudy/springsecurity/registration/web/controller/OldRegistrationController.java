package com.wangyousong.selfstudy.springsecurity.registration.web.controller;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.VerificationToken;
import com.wangyousong.selfstudy.springsecurity.registration.registration.OnRegistrationCompleteEvent;
import com.wangyousong.selfstudy.springsecurity.registration.service.IUserService;
import com.wangyousong.selfstudy.springsecurity.registration.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 18:56
 */
@Controller
@RequestMapping("/old")
@Slf4j
public class OldRegistrationController {

    @Resource
    private MessageSource messages;

    private final IUserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public OldRegistrationController(IUserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/user/registration")
    public String showRegistrationForm(final Model model) {
        log.debug("Rendering registration page.");
        final UserDto accountDto = new UserDto();
        model.addAttribute("user", accountDto);
        return "registration";
    }

    @PostMapping("/user/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid final UserDto userDto, HttpServletRequest request) {
        log.debug("Registering user account with information: {}", userDto);
        User user = userService.registerNewUserAccount(userDto);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(this, appUrl,
                request.getLocale(), user));
        return new ModelAndView("successRegister", "user", userDto);
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam("token") final String token) {
        final Locale locale = request.getLocale();

        final VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            final String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage() + "&message="
                    + model.getAttribute("message");
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            model.addAttribute("expired", true);
            model.addAttribute("token", token);

            return "redirect:/badUser.html?lang=" + locale.getLanguage() + "&message="
                    + model.getAttribute("message")
                    + "&expired=" + model.getAttribute("expired")
                    + "&token=" + model.getAttribute("token");
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
        return "redirect:/login?lang=" + locale.getLanguage() + "&message="
                + model.getAttribute("message");
    }


    @GetMapping("/user/resendRegistrationToken")
    public String resendRegistrationToken(final HttpServletRequest request, final Model model,
                                          @RequestParam("token") final String existingToken) {
        final Locale locale = request.getLocale();
        final User user = userService.getUser(existingToken);

        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/old";
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(this, appUrl,
                request.getLocale(), user, existingToken));

        model.addAttribute("message", messages.getMessage("message.resendToken", null, locale));
        return "redirect:/login?lang=" + locale.getLanguage() + "&message="
                + model.getAttribute("message");
    }

}
