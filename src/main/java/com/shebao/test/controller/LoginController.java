package com.shebao.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.shebao.test.test.factory.LoginTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginTest loginTest;

    @GetMapping("/{type}")
    public String login(@PathVariable("type") String type){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",type);
        return loginTest.login(jsonObject);
    }

    @GetMapping("/hello")
    public String login(){
        return "hello";
    }
}
