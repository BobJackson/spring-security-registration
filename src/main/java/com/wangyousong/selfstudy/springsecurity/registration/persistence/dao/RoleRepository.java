package com.wangyousong.selfstudy.springsecurity.registration.persistence.dao;

import com.wangyousong.selfstudy.springsecurity.registration.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/15 0:14
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * find role  by name
     *
     * @param name role name
     * @return Role
     */
    Role findByName(String name);
}
