package com.shebao.test.controller.personTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PersonTest {

    private List<Person> personList ;

    @Autowired
    public void PersonTest(List<Person> personList){
        for(Person p :personList){
            log.info(" ---》:{}",p.getAge());
        }

        this.personList = personList;
        log.info("----》{}",this.personList);
    }
}
