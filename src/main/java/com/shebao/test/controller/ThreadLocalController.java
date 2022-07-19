package com.shebao.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/threadlocal")
@Slf4j(topic = "测试threadLocal")
public class ThreadLocalController {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @GetMapping("/test1/{message}")
    public void test1(@PathVariable("message") String message){
        threadLocal.set(message);
        test3();
    }

    @GetMapping("/test2")
    public void test2(){
        String s = threadLocal.get();
        log.info("test2 ---> 取出的结果为:{}",s);
    }

    private void test3(){
        String s = threadLocal.get();
        log.info("test3 ---> 取出的结果为:{}",s);
    }
}
