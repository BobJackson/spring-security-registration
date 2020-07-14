package com.wangyousong.selfstudy.springsecurity.registration.service;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import com.wangyousong.selfstudy.springsecurity.registration.web.dto.UserDto;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 20:59
 */
public interface IUserService {
    /**
     * register a new user account
     * @param userDto UserDto
     * @return User
     */
    User registerNewUserAccount(UserDto userDto);
}
