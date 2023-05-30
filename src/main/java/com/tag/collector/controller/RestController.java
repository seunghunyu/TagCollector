package com.tag.collector.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @RequestMapping(value = "/test", method= RequestMethod.POST)
    public String test(){
        System.out.println("Rest 테스트용 호출");
        return "";
    }

}
