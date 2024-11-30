package com.example.securitydemo.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InValidSessionController {

    @ResponseBody
    @PostMapping("/invalidSession")
    public String createMember(){
        return "invalidSession";
    }
}
