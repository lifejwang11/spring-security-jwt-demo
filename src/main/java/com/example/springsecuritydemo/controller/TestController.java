package com.example.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/login/cas")
    public void casLogin(){
        System.out.println("cas");
    }

    @PostMapping("/login")
    public void login(){
        System.out.println("login");
    }
}
