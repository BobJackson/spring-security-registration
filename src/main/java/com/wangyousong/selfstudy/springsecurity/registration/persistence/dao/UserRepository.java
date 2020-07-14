package com.wangyousong.selfstudy.springsecurity.registration.persistence.dao;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 22:17
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * find user by email
     *
     * @param email email
     * @return User
     */
    Optional<User> findByEmail(String email);
}
