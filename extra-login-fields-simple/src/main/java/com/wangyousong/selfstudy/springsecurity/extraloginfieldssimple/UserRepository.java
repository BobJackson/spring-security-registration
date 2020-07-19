package com.wangyousong.selfstudy.springsecurity.extraloginfieldssimple;

/**
 * @author Administrator
 */
public interface UserRepository {

    /**
     * find user by username and domain
     *
     * @param username username
     * @param domain   domain
     * @return User
     */
    User findUser(String username, String domain);

}
