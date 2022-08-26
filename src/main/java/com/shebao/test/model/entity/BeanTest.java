package com.shebao.test.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BeanTest {
    private String id;
    private String name;

    // Bean初始化之后的回调
    public void init(){
        log.info("------------------ BeanTest init ------------------");
    }
    // bean 容器销毁的时候调用的回调
    public void cleanup(){
        log.info("------------------ BeanTest destroy ------------------");
    }

    // 标注这个注解，会在程序启动的的时候，执行这个方法
    @PostConstruct
    public void testPostConstruct(){
        log.info("------------------ BeanTest testPostConstruct ------------------");
    }

    // 标注这个注解，会在程序销毁的的时候，执行这个方法
    @PreDestroy
    public void testPreDestroy(){
        log.info("------------------ BeanTest PreDestroy ------------------");
    }

    // Lifecycle 接口 为任何具有自己生命周期要求的对象定义了基本方法
    /**
     * 任何 Spring 管理的对象都可以实现 Lifecycle 接口。 然后，当 ApplicationContext 本身接收到开始和停止信号时（例如，对于运行时的停止/重启场景），它会将这些调用级联到所有在其中定义的 Lifecycle 实现 语境。 它通过委托给 LifecycleProcessor 来实现这一点
     */
}
