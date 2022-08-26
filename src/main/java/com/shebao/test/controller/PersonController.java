package com.shebao.test.controller;

import com.google.common.collect.Maps;
import com.shebao.test.model.entity.Person;
import com.shebao.test.model.entity.TestApplicationEvent;
import com.shebao.test.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
@Slf4j
public class PersonController {
    @Autowired
    PersonService personService;
    @Autowired
    ApplicationContext applicationContext;

    @Resource(name = "myStringBean")  // String 作为bean可以转为java基础类型
    String myStringBean;

    @RequestMapping("/all")
    public Map<String,Object> findAll(){
        log.info("myStringBean :{}",myStringBean);
        List<Person> allPerson = personService.findAll();
        Map<String, Object> map = Maps.newHashMap();
        map.put("data",allPerson);
        return map;
    }

    /**
     * ApplicationContext 的使用
     */
    @RequestMapping("/testApplicationContext")
    public void testApplicationContext(){
        log.info("test applicationContext");
        String str = "test applicationContext";
        TestApplicationEvent testApplicationEvent = new TestApplicationEvent(str);
        applicationContext.publishEvent(testApplicationEvent);
        log.info("发布消息结束");
    }

    /**
     * @EventListener注解的使用
     * @param event
     */
    @EventListener(TestApplicationEvent.class)
    public void testAcceptApplicationContest(TestApplicationEvent event) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        String str =(String) event.getSource();
        log.info("--->{}",str);
    }
}
