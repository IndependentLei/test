package com.shebao.test.model.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class A {
    protected String name;

    @Autowired
    public com.shebao.test.config.beanTest.InstantiationTracingBeanPostProcessor InstantiationTracingBeanPostProcessor;
}
