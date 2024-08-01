package com.tag.collector.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void findById(){
        String userId = "yush";

        if(userService.findById(userId)){
            log.info("user exist");
        }
    }
    @Test
    void login(){
        String userId = "yush";
        String password = "yush123";

        if(userService.login(userId, password)){
            log.info("login success");
        }else{
            log.info("login fail");
        }
    }
    @Test
    @Transactional
    void joinMember(){
        String userId = "jone";
        String userName = "김종원";
        String password = "jone123";

        if(userService.joinMember(userId, userName ,password)){
            log.info("join success");
        }else{
            log.info("join fail");
        }

        if(userService.findById(userId)){
            log.info("user exist");
        }
    }
}