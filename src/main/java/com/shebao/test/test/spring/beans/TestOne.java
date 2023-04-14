package com.shebao.test.test.spring.beans;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

public class TestOne {

    @Test
    public void test1(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        Object bean = factory.getBean("33");
        System.out.println(bean);
    }
}
