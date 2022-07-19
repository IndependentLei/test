package com.shebao.test.springRabbitMq.controller;

import cn.hutool.core.lang.UUID;
import com.shebao.test.constant.RabbitMqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @Description :
 * @ClassName : ProducerController
 * @Author : jdl
 * @Create : 2022-07-19 22:23
 */
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{msg}")
    public void sendMessage(@PathVariable("msg") String msg){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());  // 发布确认的值
        log.info("在{}发送消息：{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),msg);
        rabbitTemplate.convertAndSend(RabbitMqConstant.CONFIRM_EXCHANGE_NAME,RabbitMqConstant.CONFIRM_ROUTING_KEY,msg,correlationData);
    }
}
