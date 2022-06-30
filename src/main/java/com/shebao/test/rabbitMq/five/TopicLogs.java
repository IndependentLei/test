package com.shebao.test.rabbitMq.five;

import com.google.common.collect.Maps;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.shebao.test.rabbitMq.utils.RabbitMqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 主题模式，发布者
 */
public class TopicLogs {
    public static final String TOPIC_NAME = "topic_logs";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtil.createChannel();
        Map<String,String> bindingKeyMap = Maps.newHashMap();
        bindingKeyMap.put("quick.orange.rabbit","被队列Q1Q2接受到");
        bindingKeyMap.put("lazy.orange.elephant","被队列Q1Q2接受到");
        bindingKeyMap.put("quick.orange.fox","被队列Q1接受到");
        bindingKeyMap.put("lazy.brown.fox","被队列Q2接受到");
        bindingKeyMap.put("lazy.pink.rabbit","虽然满足两个绑定但只能被队列Q2接受一次");
        bindingKeyMap.put("quick.brown.fox","不匹配任何绑定不会被任何队列接受到会被丢弃");
        bindingKeyMap.put("quick.orange.male.rabbit","是四个单词不匹配任何绑定会被丢弃");
        bindingKeyMap.put("lazy.orange.male.rabbit","是四个单词单匹配Q2");
        for(Map.Entry<String,String> entry : bindingKeyMap.entrySet()){
            channel.basicPublish(TOPIC_NAME,entry.getKey(),null,entry.getValue().getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息："+entry.getValue());
        }
    }
}
