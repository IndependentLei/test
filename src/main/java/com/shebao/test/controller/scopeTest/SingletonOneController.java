package com.shebao.test.controller.scopeTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SingletonOneController {


    @Autowired
    private SingletonBean singletonBean;

    @GetMapping("/singletonOne")
    public void singleton(){
        log.info("singletonOne >>>>>>>> {}",this.singletonBean);
    }

    /**
     * 2022-09-22 11:29:42.503  INFO 19264 --- [nio-9191-exec-1] c.s.t.c.s.PrototypeOneController         : prototypeOne >>>>>>>>> com.shebao.test.controller.scopeTest.PrototypeBean@441016d6
     * 2022-09-22 11:29:48.243  INFO 19264 --- [nio-9191-exec-2] c.s.t.c.s.PrototypeTwoController         : prototypeTwo >>>>>>>>> com.shebao.test.controller.scopeTest.PrototypeBean@6a3a56de

     * 2022-09-22 11:30:11.081  INFO 19264 --- [nio-9191-exec-3] c.s.t.c.s.SingletonOneController         : singletonOne >>>>>>>> com.shebao.test.controller.scopeTest.SingletonBean@400e741
     * 2022-09-22 11:30:16.067  INFO 19264 --- [nio-9191-exec-5] c.s.t.c.s.SingletonTwoController         : singletonTwo >>>>>>>> com.shebao.test.controller.scopeTest.SingletonBean@400e741
     */
}
