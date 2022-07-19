package com.shebao.test.springRabbitMq.consumerListening;

import com.rabbitmq.client.Channel;
import com.shebao.test.constant.RabbitMqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description :
 * @ClassName : ConsumerListening
 * @Author : jdl
 * @Create : 2022-07-11 21:59
 */
@Component
@Slf4j
public class ConsumerListening {

    // 监听队列
    @RabbitListener(queues = RabbitMqConstant.DEAD_LETTER_QUEUE)
    public void acceptMsg(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("时间为：{},接收到的消息为：{}",new Date().toString(),msg);
    }

    // 监听队列
    @RabbitListener(queues = RabbitMqConstant.DELAY_QUEUE_NAME)
    public void acceptDelayMsg(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("时间为：{},接收到的消息为：{}",new Date().toString(),msg);
    }

    @RabbitListener(queues = RabbitMqConstant.CONFIRM_QUEUE_NAME)
    public void acceptConfirmMsg(Message message,Channel channel){
        String msg = new String(message.getBody());
        log.info("时间为：{},接收到的消息为：{}",new Date().toString(),msg);
    }
}
