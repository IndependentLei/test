package com.shebao.test.rabbitMq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.shebao.test.rabbitMq.utils.RabbitMqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * rabbitMq的预取值
 */
public class Producer {
    // 队列名
    public static final String QUEUE_NAME = "hello";
    // 发布消息
    public static void main(String[] args) {
        try {
            Channel channel = RabbitMqUtil.createChannel();
            // 信道开启发布确认
            channel.confirmSelect();
            boolean durable = true; // 需要让Queue进行持久化
            channel.queueDeclare(
                    QUEUE_NAME,
                    durable,
                    false,
                    false,
                    null
            );
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String message = scanner.next();
                try {
                    // 设置生产者发送消息为持久化消息，（要求保持到磁盘上），保存在内存中
                    channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN
                            ,message.getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("消息发送完毕:"+message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
