package com.shebao.test.controller.personTest;

import org.springframework.stereotype.Service;

@Service
public class Man implements Person{

    @Override
    public int getAge() {
        return 3;
    }
}
