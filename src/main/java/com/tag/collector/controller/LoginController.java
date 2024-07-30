package com.tag.collector.controller;

import com.tag.collector.data.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){

        log.info("::::::::::::::::::::::LOGIN PAGE:::::::::::::::::::::::::");

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
        log.info("::::::::::::::::::::::회원가입:::::::::::::::::::::::::");

        return "register";
    }

    @PostMapping("/join")
    public String join(UserInfo userInfo){

        log.info("join form");
        log.info("join info =" + userInfo.toString());

        return "login";
    }


}
