package com.shebao.test.rabbitMq.five;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.shebao.test.rabbitMq.utils.RabbitMqUtil;

import java.io.IOException;

/**
 * 主题模式，消费者三
 */
public class ReceiveLogTopic03 {

    public static final String TOPIC_NAME = "topic_logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtil.createChannel();
        // 声明交换机
        channel.exchangeDeclare(TOPIC_NAME, BuiltinExchangeType.TOPIC);
        // 声明队列
        String QUEUE_NAME = "Q2";
        String ROUTING_KEY = "lazy.#";
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 将队列和交换机绑定
        channel.queueBind(
                QUEUE_NAME,
                TOPIC_NAME,
                ROUTING_KEY
        );
        System.out.println("等待接收消息，把接收到消息打印在屏幕上......");

        // 成功接收消息回调函数
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("ReceiveLogTopic03---->控制台打印接收到的消息:"+new String(message.getBody()));
            System.out.println("接收队列："+QUEUE_NAME+"绑定键："+message.getEnvelope().getRoutingKey());
        };
        // 接收消息
        channel.basicConsume(QUEUE_NAME,deliverCallback,consumerTag->{});
    }
}
