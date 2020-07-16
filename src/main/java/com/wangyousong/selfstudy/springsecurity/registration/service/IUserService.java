package com.wangyousong.selfstudy.springsecurity.registration.service;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.PasswordResetToken;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.VerificationToken;
import com.wangyousong.selfstudy.springsecurity.registration.web.dto.UserDto;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 20:59
 */
public interface IUserService {
    /**
     * register a new user account
     *
     * @param userDto UserDto
     * @return User
     */
    User registerNewUserAccount(UserDto userDto);

    /**
     * create verification token for user
     *
     * @param user  user
     * @param token token
     */
    void createVerificationTokenForUser(User user, String token);

    /**
     * getVerificationToken
     *
     * @param token token
     * @return VerificationToken
     */
    VerificationToken getVerificationToken(String token);

    /**
     * saveRegisteredUser -> enable user
     *
     * @param user user
     */
    void saveRegisteredUser(User user);

    /**
     * getUser by existingToken
     *
     * @param existingToken existingToken
     * @return User
     */
    User getUser(String existingToken);

    /**
     * update verification token for user
     *
     * @param user  user
     * @param token token
     * @return a new VerificationToken
     */
    VerificationToken updateVerificationTokenForUser(User user, String token);

    /**
     * findUserByEmail
     *
     * @param userEmail userEmail
     * @return User
     */
    User findUserByEmail(String userEmail);

    /**
     * create PasswordResetToken for user
     *
     * @param user  user
     * @param token token
     */
    void createPasswordResetTokenForUser(User user, String token);

    /**
     * getPasswordResetToken from token
     *
     * @param token token
     * @return PasswordResetToken
     */
    PasswordResetToken getPasswordResetToken(String token);

    /**
     * change user password
     *
     * @param user     user
     * @param password password
     */
    void changeUserPassword(User user, String password);
}
