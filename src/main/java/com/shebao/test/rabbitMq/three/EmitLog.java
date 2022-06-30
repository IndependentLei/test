package com.shebao.test.rabbitMq.three;

import com.rabbitmq.client.Channel;
import com.shebao.test.rabbitMq.utils.RabbitMqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 发消息/交换机
 */
public class EmitLog {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtil.createChannel();
//        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发布消息->"+message);
        }
    }
}
