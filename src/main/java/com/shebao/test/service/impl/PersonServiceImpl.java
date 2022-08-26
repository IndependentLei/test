package com.shebao.test.service.impl;

import com.shebao.test.mapper.PersonMapper;
import com.shebao.test.model.entity.Person;
import com.shebao.test.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService , InitializingBean, DisposableBean {

    @Autowired
    PersonMapper personMapper;

    @Override
    public List<Person> findAll() {
        return personMapper.findAll();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // spring 加载 bean 的回调
        log.info("------------ PersonServiceImpl afterPropertiesSet ------------");
    }

    @Override
    public void destroy() throws Exception {
        log.info("------------ PersonServiceImpl destroy ------------");
    }
}
