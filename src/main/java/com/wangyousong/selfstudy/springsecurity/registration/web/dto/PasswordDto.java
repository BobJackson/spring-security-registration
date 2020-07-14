package com.wangyousong.selfstudy.springsecurity.registration.web.dto;

import com.wangyousong.selfstudy.springsecurity.registration.validation.ValidPassword;
import lombok.Data;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 22:03
 */
@Data
public class PasswordDto {

    private String oldPassword;
    private String token;
    @ValidPassword
    private String newPassword;
}
