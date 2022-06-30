package com.shebao.test.rabbitMq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.shebao.test.rabbitMq.utils.RabbitMqUtil;
import org.checkerframework.checker.units.qual.C;
import org.testng.collections.Maps;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 消费者一
 */
public class Consumer01 {

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtil.createChannel();
        // 普通交换机
        channel.exchangeDeclare(Constant.NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 死信交换机
        channel.exchangeDeclare(Constant.DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);

        Map<String,Object> arguments = Maps.newHashMap();
        // 过期时间，可以由发送者设置
//        arguments.put("x-message-ttl",100000); // 毫秒
        // 正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange",Constant.DEAD_EXCHANGE);
        // 设置死信routing_key
        arguments.put("x-dead-letter-routing-key",Constant.DEAD_ROUTING_KEY);
        // 普通队列
        channel.queueDeclare(Constant.NORMAL_QUEUE,false,false,false,null);
        // 死信队列
        channel.queueDeclare(Constant.DEAD_QUEUE,false,false,false,null);

        // 绑定普通的交换机与普通的队列
        channel.queueBind(
                Constant.NORMAL_QUEUE,
                Constant.NORMAL_EXCHANGE,
                Constant.NORMAL_ROUTING_KEY
        );
        // 绑定死信交换机与死信的队列
        channel.queueBind(
                Constant.DEAD_QUEUE,
                Constant.DEAD_EXCHANGE,
                Constant.DEAD_ROUTING_KEY
        );

        System.out.println("等待接收消息。。。");
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("消费者一---->Consumer01接收的消息是："+new String(message.getBody(), StandardCharsets.UTF_8));
        };
        // 接收消息
        channel.basicConsume(Constant.NORMAL_QUEUE,false,deliverCallback,consumerTag -> {});
    }

}
