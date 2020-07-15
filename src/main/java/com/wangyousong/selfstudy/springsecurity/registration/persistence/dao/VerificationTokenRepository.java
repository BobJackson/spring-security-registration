package com.wangyousong.selfstudy.springsecurity.registration.persistence.dao;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/15 17:14
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    /**
     * findByToken
     *
     * @param token token
     * @return optional verification token
     */
    Optional<VerificationToken> findByToken(String token);
}
