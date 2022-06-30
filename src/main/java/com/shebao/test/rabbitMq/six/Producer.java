package com.shebao.test.rabbitMq.six;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.shebao.test.rabbitMq.utils.RabbitMqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/***
 * 死信---生产者
 */
public class Producer {
    public static void main(String[] args) {
        Channel channel = RabbitMqUtil.createChannel();
        // 设置过期时间 10s
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 1; i < 11; i++) {
            String message = "info-->"+i;
            try {
                // 发送消息
                channel.basicPublish(Constant.NORMAL_EXCHANGE,Constant.NORMAL_ROUTING_KEY,properties,message.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
