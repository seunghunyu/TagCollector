package com.tag.collector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @RequestMapping("/home")
    public String home(){
        System.out.println("home");
        return "index.html";
    }
}
