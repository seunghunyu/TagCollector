package com.tag.collector.controller;

import com.tag.collector.data.UserInfo;
import com.tag.collector.service.UserService;
import com.tag.collector.util.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class LoginController {
    @Autowired
    UserService userService;

    Utility util;

    //로그인 화면 이동
    @GetMapping("/login")
    public String login(){
        log.info("::::::::::::::::::::::LOGIN PAGE:::::::::::::::::::::::::");
        return "login";
    }

    //로그인버튼 클릭 시 검증
    @PostMapping("/login")
    public String login(@RequestBody UserInfo userInfo){
        if(userService.login(userInfo.getUserId(),userInfo.getPassword())){

            util = new Utility();
            util.createSecureCookie("");
            return "home";
        }
        return "login";
    }

    //로그아웃
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

    //회원등록버튼 클릭
    @PostMapping("/register")
    public String join(UserInfo userInfo){
        log.info("join info =" + userInfo.toString());

        if(userService.joinMember(userInfo.getUserId(),userInfo.getUserName(), userInfo.getPassword())){
            return "login";
        }

        return "register";
    }



}
