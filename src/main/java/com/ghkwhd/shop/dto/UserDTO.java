package com.ghkwhd.shop.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {

    @NotBlank(message="id는 필수 입력값 입니다")
    private String id;
    @NotBlank(message="이름은 필수 입력값 입니다")
    private String name;
    @NotBlank(message="비밀번호는 필수 입력값 입니다")
    private String password;
    @NotBlank(message="이메일은 필수 입력값 입니다")
    @Email
    private String email;
}
