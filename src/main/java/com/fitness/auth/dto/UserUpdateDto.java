package com.fitness.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserUpdateDto {
    private Integer id;
    private String username;
    private String fullName;
    private Integer age;
    private Timestamp dob;
    private Integer roleId;
}
