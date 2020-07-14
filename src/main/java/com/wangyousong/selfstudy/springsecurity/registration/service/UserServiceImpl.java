package com.wangyousong.selfstudy.springsecurity.registration.service;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.dao.UserRepository;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import com.wangyousong.selfstudy.springsecurity.registration.web.dto.UserDto;
import com.wangyousong.selfstudy.springsecurity.registration.web.error.UserAlreadyExistException;
import org.springframework.stereotype.Service;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 20:59
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerNewUserAccount(UserDto accountDto) {
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());

        return userRepository.save(user);
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
