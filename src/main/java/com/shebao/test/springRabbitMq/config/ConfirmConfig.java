package com.shebao.test.springRabbitMq.config;

import com.shebao.test.constant.RabbitMqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : 发布确认配置类
 * @ClassName : ConfirmConfig
 * @Author : jdl
 * @Create : 2022-07-19 22:11
 */
@Configuration
public class ConfirmConfig {

    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return new DirectExchange(RabbitMqConstant.CONFIRM_EXCHANGE_NAME);
    }

    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return new Queue(RabbitMqConstant.CONFIRM_QUEUE_NAME);
    }

    @Bean
    public Binding confirmQueueBindingConfirmExchange(@Qualifier("confirmExchange") DirectExchange directExchange,
                                                      @Qualifier("confirmQueue") Queue confirmQueue){
        return BindingBuilder.bind(confirmQueue).to(directExchange).with(RabbitMqConstant.CONFIRM_ROUTING_KEY);
    }
}
