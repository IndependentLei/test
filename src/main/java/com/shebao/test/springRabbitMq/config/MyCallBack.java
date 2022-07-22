package com.shebao.test.springRabbitMq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
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
public class MyCallBack implements RabbitTemplate.ConfirmCallback , RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct // PostConstruct在构造函数之后执行 Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
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
            log.info("交换机已经收到了消息,ID为：{}",id);
        }else {
            log.info("交换机还没有收到ID为{}的消息，失败原因：{}",id,cause);
        }
    }

//    /**
//     * 可以在当消息传递过程中不可达目的地时将消息返回给生产者
//     * @param message
//     * @param replyCode
//     * @param replyText
//     * @param exchange
//     * @param routingKey
//     */
//    @Override
//    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        log.error("消息：{}，被交换机{}退回原因：{}，路由{}",message,exchange,replyText,routingKey);
//    }

    /**
     * 可以在当消息传递过程中不可达目的地时将消息返回给生产者
     * @param returned
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息：{}，被交换机{}退回原因：{}，路由{}"
                ,returned.getMessage()
                ,returned.getExchange()
                ,returned.getReplyText()
                ,returned.getRoutingKey());
    }
}
