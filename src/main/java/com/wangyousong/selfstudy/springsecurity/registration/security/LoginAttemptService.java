package com.wangyousong.selfstudy.springsecurity.registration.security;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/18 15:53
 */
public interface LoginAttemptService {
    /**
     * loginSucceeded
     *
     * @param key key
     */
    void loginSucceeded(String key);

    /**
     * loginFailed
     *
     * @param key key
     */
    void loginFailed(String key);

    /**
     * isBlocked
     *
     * @param key key
     * @return is blocked or not
     */
    boolean isBlocked(String key);
}
