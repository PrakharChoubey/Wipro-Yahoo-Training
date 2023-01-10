package com.springboot.demo1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloCOntroller {
//    @RequestMapping(value = "/",method = RequestMethod.GET)
    @Value("${welcome.message}")
    private String welcomeMessage;
    @GetMapping("/")
    public String hello(){
        return welcomeMessage;
    }
}
