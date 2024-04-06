package com.fitness.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserRegistrationDto {
    private String username;
    private String password;
    private String fullName;
    private Integer age;
    private Timestamp dob;
}
