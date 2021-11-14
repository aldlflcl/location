package com.aldlflcl.location.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginForm {

    @NotEmpty(message = "올바른 이메일을 입력해주세요")
    @Email(message = "올바른 이메일을 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    private String name;

    private int userId;
}
