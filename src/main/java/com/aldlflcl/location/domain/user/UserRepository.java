package com.aldlflcl.location.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepository {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper mapper;

    @Transactional
    public void createUser(RegisterForm userForm, BindingResult bindingResult) {
        Integer emailCheck = mapper.checkEmail(userForm);
        Integer checkName = mapper.checkName(userForm);

        if (emailCheck != 0 || checkName != 0) {

            if (emailCheck != 0) {
                bindingResult.rejectValue("email", null, "이미 가입된 이메일입니다.");
            }

            if (checkName != 0) {
                bindingResult.rejectValue("name", null, "중복된 이름입니다.");
            }
            return;
        }

        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
        mapper.createUser(userForm);
    }

    public LoginForm loginUser(LoginForm form, BindingResult bindingResult) {
        LoginForm loginForm = mapper.getByEmail(form.getEmail());
        if (loginForm == null) {
            bindingResult.reject("loginError", null, "아이디 또는 비밀번호 오류입니다.");
            return null;
        }
        if (!passwordEncoder.matches(form.getPassword(), loginForm.getPassword())) {
            bindingResult.reject("loginError", null, "아이디 또는 비밀번호 오류입니다.");
            return null;
        }
        return loginForm;
    }

    public User getById(int userId) {
        return mapper.getById(userId);
    }
}
