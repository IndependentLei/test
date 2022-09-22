package com.shebao.test.controller.scopeTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PrototypeOneController {
    @Autowired
    PrototypeBean prototypeBean;

    @GetMapping("/prototypeOne")
    public void prototype(){
        log.info("prototypeOne >>>>>>>>> {}",prototypeBean);
    }
}
