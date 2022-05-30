package com.shebao.test.service.impl;

import com.shebao.test.mapper.PersonMapper;
import com.shebao.test.model.entity.Person;
import com.shebao.test.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonMapper personMapper;

    @Override
    public List<Person> findAll() {
        return personMapper.findAll();
    }
}
