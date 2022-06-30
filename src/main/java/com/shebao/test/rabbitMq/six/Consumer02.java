package com.shebao.test.rabbitMq.six;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.shebao.test.rabbitMq.utils.RabbitMqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Consumer02 {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtil.createChannel();
        System.out.println("等待接收消息。。。");
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("消费者一---->Consumer02接收的消息是："+new String(message.getBody(), StandardCharsets.UTF_8));
        };
        // 接收消息
        channel.basicConsume(Constant.DEAD_QUEUE,false,deliverCallback,consumerTag -> {});
    }
}
