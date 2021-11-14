package com.aldlflcl.location.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RegisterForm {

    private int userId;

    @NotEmpty
    @Email(message = "올바른 이메일을 입력해주세요.")
    private String email;

    @NotEmpty(message = "사용하실 이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
}
