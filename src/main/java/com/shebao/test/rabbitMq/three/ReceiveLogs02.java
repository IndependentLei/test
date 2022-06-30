package com.shebao.test.rabbitMq.three;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.shebao.test.rabbitMq.utils.RabbitMqUtil;

import java.io.IOException;

/**
 * 消息接收
 */
public class ReceiveLogs02 {
    // 交换机的名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtil.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // 声明一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        // 绑定交换机与队列
        channel.queueBind(
                queueName,
                EXCHANGE_NAME,
                ""
        );
        System.out.println("等待接收消息，把接收到消息打印在屏幕上......");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("ReceiveLogs02---->控制台打印接收到的消息:"+new String(message.getBody()));
        };
        channel.basicConsume(queueName,true,deliverCallback,consumerTag -> {});
    }
}
