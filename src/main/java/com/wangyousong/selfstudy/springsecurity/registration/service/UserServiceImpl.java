package com.wangyousong.selfstudy.springsecurity.registration.service;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import com.wangyousong.selfstudy.springsecurity.registration.web.dto.UserDto;
import org.springframework.stereotype.Service;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 20:59
 */
@Service
public class UserServiceImpl implements IUserService{

    @Override
    public User registerNewUserAccount(UserDto userDto) {
        return null;
    }
}
