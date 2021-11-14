package com.aldlflcl.location.web.user;

import com.aldlflcl.location.domain.user.LoginForm;
import com.aldlflcl.location.domain.user.RegisterForm;
import com.aldlflcl.location.domain.user.UserRepository;
import com.aldlflcl.location.web.SessionConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository repository;

    @GetMapping("/register")
    public String registerForm(@ModelAttribute("userForm") RegisterForm form) {

        return "user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userForm") @Validated RegisterForm form, BindingResult bindingResult) {

        repository.createUser(form, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "user/register";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("userForm") LoginForm form) {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("userForm") LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        LoginForm loginForm = repository.loginUser(form, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("loginError={}", bindingResult);
            return "user/login";
        }

        session.setAttribute(SessionConstant.LOGIN_USER, loginForm);
        String name = loginForm.getName();
        redirectAttributes.addFlashAttribute("success", "돌아오신것을 환영합니다. " + name +"님");

        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
