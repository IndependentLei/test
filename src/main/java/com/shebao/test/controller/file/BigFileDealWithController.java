package com.shebao.test.controller.file;

import com.shebao.test.service.BigFileDealWithService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bigFile")
public class BigFileDealWithController {

    @Resource
    BigFileDealWithService bigFileDealWithService;

    @PostMapping("/dealWith")
    public void dealWith(){
        bigFileDealWithService.dealWith();
    }

    @PostMapping("/merge")
    public void merge(){
        bigFileDealWithService.merge();
    }

}
