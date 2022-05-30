package com.shebao.test.controller;

import com.google.common.collect.Maps;
import com.shebao.test.model.entity.Person;
import com.shebao.test.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class PersonController {
    @Autowired
    PersonService personService;

    @RequestMapping("/all")
    public Map<String,Object> findAll(){
        List<Person> allPerson = personService.findAll();
        Map<String, Object> map = Maps.newHashMap();
        map.put("data",allPerson);
        return map;
    }
}
