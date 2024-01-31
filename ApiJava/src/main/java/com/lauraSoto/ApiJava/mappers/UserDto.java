package com.lauraSoto.ApiJava.mappers;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

    private String name;

    private String email;

    private Date createdAt;

    private Date updatedAt;

    private Date lastLogin;

    private Boolean isActive;

    private String token;
}
