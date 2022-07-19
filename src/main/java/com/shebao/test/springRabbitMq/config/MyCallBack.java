package com.shebao.test.springRabbitMq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @Description : 发布确认 回调函数类
 * @ClassName : MyCallBack
 * @Author : jdl
 * @Create : 2022-07-19 22:36
 */
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct // PostConstruct在构造函数之后执行 Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
    }
    /**
     * @param correlationData  保存回调消息的ID及相关信息
     * @param ack 交换机收到消息
     * @param cause 失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = Objects.isNull(correlationData) ? "":correlationData.getId();
        if(ack){
            log.info("交换机已经收到了消息,ID为：{}，",id);
        }
    }
}
