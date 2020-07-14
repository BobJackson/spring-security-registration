package com.wangyousong.selfstudy.springsecurity.registration.web.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Bob
 * @version 1.0
 * @date 2020/7/14 19:00
 */
@Data
public class UserDto {
    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    private String lastName;

    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;
}
