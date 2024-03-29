package com.shebao.test.springRabbitMq.controller;

import com.shebao.test.constant.RabbitMqConstant;
import com.shebao.test.model.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description :
 * @ClassName : SendMessageController
 * @Author : jdl
 * @Create : 2022-07-11 21:49
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMsg (@PathVariable("message") String message){
        log.info("当前时间为:{},发送的消息为:{}", new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(new Date()),message);
        rabbitTemplate.convertAndSend(RabbitMqConstant.X_EXCHANGE,RabbitMqConstant.X_ROUTING_KEY_QA,"死信时间为10秒的消息："+message);
        rabbitTemplate.convertAndSend(RabbitMqConstant.X_EXCHANGE,RabbitMqConstant.X_ROUTING_KEY_QB,"死信时间为40秒的消息："+message);
    }

    @GetMapping("/sendMsg/{message}/{delayTime}")
    public void sendDelayTimeMsg(@PathVariable("message") String message,@PathVariable("delayTime") String delayTime){
        log.info("当前时间为:{},发送的消息为:{},延迟时长为:{}",new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(new Date()),message,delayTime);
        rabbitTemplate.convertAndSend(RabbitMqConstant.X_EXCHANGE,RabbitMqConstant.X_ROUTING_KEY_QC,message,messagePostProcessor->{
            // 设置发送消息的时候，延迟时间
            messagePostProcessor.getMessageProperties().setExpiration(delayTime);
            return messagePostProcessor;
        });
    }

    /**
     * 基于插件的延迟队列
     */
    @GetMapping("/sendMsgByPlugin/{message}/{delayTime}")
    public void sendDelayMsgByPlugin(@PathVariable("message") String message, @PathVariable("delayTime") Integer delayTime){
        log.info("当前时间为:{},发送的消息为:{},延迟时长为:{}",new Date().toString(),message,delayTime);
        rabbitTemplate.convertAndSend(RabbitMqConstant.DELAY_EXCHANGE_NAME,RabbitMqConstant.DELAY_ROUTING_KEY,message,messagePostProcessor->{
            // 设置发送消息的时候，延迟时间
            messagePostProcessor.getMessageProperties().setDelay(delayTime);
            return messagePostProcessor;
        });
    }

    @GetMapping("/sendMsgTest")
    public void sendDelayMsgByPlugin(){
        Person person = new Person();
        person.setId(20L);
        person.setAge("3");
        person.setName("jj");
        log.info("当前时间为:{}",new Date().toString());
        rabbitTemplate.convertAndSend(RabbitMqConstant.TEST_EXCHANGE_NAME,"",person);
    }

}
