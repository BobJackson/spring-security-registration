package com.wangyousong.selfstudy.springsecurity.registration.persistence.dao;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/16 19:17
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    /**
     * findByToken
     *
     * @param token token
     * @return optional password reset token
     */
    Optional<PasswordResetToken> findByToken(String token);
}
