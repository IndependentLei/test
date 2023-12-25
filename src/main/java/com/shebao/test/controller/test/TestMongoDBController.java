package com.shebao.test.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mongodb")
public class TestMongoDBController {

    @GetMapping("/test")
    public String insert(){
        return "test";
    }

    @PostMapping("delete")
    public String delete(){
        return "delete";
    }
}
