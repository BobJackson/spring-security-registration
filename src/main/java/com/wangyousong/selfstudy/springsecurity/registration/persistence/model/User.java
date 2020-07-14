package com.wangyousong.selfstudy.springsecurity.registration.persistence.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 18:46
 */
@Entity
@Table(name = "user_account")
@Data
public class User {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @Column(length = 60)
    private String password;
}
