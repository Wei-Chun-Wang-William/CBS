package com.wwc.project.bean.dto;

import lombok.Data;

@Data
public class CreateUserRequestDto {
    private String userName;
    private String email;
    private String phone;
    private String password;
}
