package com.shebao.test.controller.scopeTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SingletonTwoController {

    @Autowired
    private SingletonBean singletonBean;

    @GetMapping("/singletonTwo")
    public void singleton(){
        log.info("singletonTwo >>>>>>>> {}",this.singletonBean);
    }
}
