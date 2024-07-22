package com.tag.collector.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class LoginController {
    int idx = 0;
    @GetMapping("/login")
    public String login(){
        idx++;
        log.info("::::::::::::::::::::::LOGIN PAGE:::::::::::::::::::::::::"+Integer.toString(idx));

        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        log.info("::::::::::::::::::::::LOGOUT:::::::::::::::::::::::::");
        return "index";
    }

    //회원가입 화면
    @GetMapping("/register")
    public String register(){
        log.info("::::::::::::::::::::::LOGOUT:::::::::::::::::::::::::");
        return "register";
    }
}
