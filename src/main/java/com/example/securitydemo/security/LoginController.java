package com.example.securitydemo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if(null != error) {
            model.addAttribute("errorMsg", "로그인 실패. 다시 시도하세요.");
        }
        return "loginForm";
    }

}
