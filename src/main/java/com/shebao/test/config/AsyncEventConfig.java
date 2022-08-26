package com.shebao.test.config;

import com.shebao.test.model.entity.BeanTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * applicationContext 异步执行配置类
 */
@Configuration
public class AsyncEventConfig {

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster
                = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }

    @Bean(name = "myStringBean")
    public String getString(){
        return new String("true");
    }

    // 定义初始化之后执行的方法和销毁之前执行的方法
    @Bean(initMethod = "init",destroyMethod = "cleanup")
    public BeanTest getBeanTest(){
        return new BeanTest();
    }

}
