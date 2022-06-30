package com.shebao.test.rabbitMq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqUtil {
    public static Channel createChannel(){
        Connection connection;
        Channel channel = null;
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
    }
}
