package com.wangyousong.selfstudy.springsecurity.registration.web.controller;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import com.wangyousong.selfstudy.springsecurity.registration.service.IUserService;
import com.wangyousong.selfstudy.springsecurity.registration.web.dto.UserDto;
import com.wangyousong.selfstudy.springsecurity.registration.web.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 18:56
 */
@Controller
@RequestMapping("/old")
@Slf4j
public class OldRegistrationController {

    private final IUserService userService;

    @Resource
    private MessageSource messages;

    public OldRegistrationController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/registration")
    public String showRegistrationForm(final Model model) {
        log.debug("Rendering registration page.");
        final UserDto accountDto = new UserDto();
        model.addAttribute("user", accountDto);
        return "registration";
    }

    @PostMapping("/user/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid final UserDto userDto, final HttpServletRequest request) {
        log.debug("Registering user account with information: {}", userDto);

        try {
            userService.registerNewUserAccount(userDto);
        } catch (final UserAlreadyExistException uaeEx) {
            ModelAndView mav = new ModelAndView("registration", "user", userDto);
            String errMessage = messages.getMessage("message.regError", null, request.getLocale());
            mav.addObject("message", errMessage);
            return mav;
        } catch (final RuntimeException ex) {
            LOGGER.warn("Unable to register user", ex);
            return new ModelAndView("emailError", "user", userDto);
        }
        return new ModelAndView("successRegister", "user", userDto);
    }
}
