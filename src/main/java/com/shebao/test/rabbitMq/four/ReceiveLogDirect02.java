package com.shebao.test.rabbitMq.four;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.shebao.test.rabbitMq.utils.RabbitMqUtil;

import java.io.IOException;

public class ReceiveLogDirect02 {
    // 交换机的名称
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtil.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT); // 直接交换机
        // 声明一个临时队列
        String QUEUE_NAME = "console";
        String ROUTING_KEY = "warning";
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 绑定交换机与队列
        channel.queueBind(
                QUEUE_NAME,
                EXCHANGE_NAME,
                ROUTING_KEY
        );
        System.out.println("等待接收消息，把接收到消息打印在屏幕上......");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("ReceiveLogDirect02---->控制台打印接收到的消息:"+new String(message.getBody()));
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,consumerTag -> {});
    }
}
