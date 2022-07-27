package com.shebao.test.springRabbitMq.consumerListening;

import com.rabbitmq.client.Channel;
import com.shebao.test.constant.RabbitMqConstant;
import com.shebao.test.model.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
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

    @RabbitListener(queues = RabbitMqConstant.WARNING_QUEUE_NAME)
    public void acceptWarningMsg(Message message,Channel channel){
        String msg = new String(message.getBody());
        log.error("发现不可路由的消息，时间为：{},接收到的消息为：{}",new Date().toString(),msg);
    }

    // 使用注解 将队列和交换机绑定
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = RabbitMqConstant.TEST_QUEUE_NAME,durable = "true", ignoreDeclarationExceptions = "true")
            , exchange = @Exchange(value = RabbitMqConstant.TEST_EXCHANGE_NAME,type = ExchangeTypes.FANOUT,durable = "true")
            ,ignoreDeclarationExceptions = "true"))
    public void testAcceptMsg(@Payload Person person, Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("发现路由消息，时间为：{},接收到的消息为：{},接收到的实体为：{}",new Date().toString(),msg,person);
    }
}
