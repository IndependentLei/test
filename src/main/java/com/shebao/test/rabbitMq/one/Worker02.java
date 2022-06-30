package com.shebao.test.rabbitMq.one;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.shebao.test.rabbitMq.utils.RabbitMqUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 消费者2
 */
public class Worker02 {
    // 队列名称
    public static final String QUEUE_NAME = "hello";

    // 接收消息
    public static void main(String[] args) {
        Channel channel = RabbitMqUtil.createChannel();


        // 消息接收
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("接收到的消息："+ new String(message.getBody()));
        };
        // 设置不公平分发，为1是不公平分发，其他为预取值
        int prefetchCount = 2;
        try {
            channel.basicQos(prefetchCount);
            TimeUnit.SECONDS.sleep(2);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        // 消息接收被取消时，执行下面的内容
        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println(consumerTag+":消费者取消消费者接口回调逻辑");
        };
        try {
            System.out.println("线程二---->等待接收消息。。。。。。");
            channel.basicConsume(
                    QUEUE_NAME, //队列名
                    true,
                    deliverCallback,
                    cancelCallback
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
