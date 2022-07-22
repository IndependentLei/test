package com.shebao.test.springRabbitMq.config;

import com.shebao.test.constant.RabbitMqConstant;
import org.springframework.amqp.core.*;
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
        return ExchangeBuilder.directExchange(RabbitMqConstant.CONFIRM_EXCHANGE_NAME)
                .durable(true)
                // 指定转发的交换机
                .withArgument("alternate-exchange",RabbitMqConstant.BACKUP_EXCHANGE_NAME).build();
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

    @Bean("backupExchange")
    public FanoutExchange backUpExchange(){
        return new FanoutExchange(RabbitMqConstant.BACKUP_EXCHANGE_NAME);
    }

    @Bean("backupQueue")
    public Queue backupQueue(){
        return QueueBuilder.durable(RabbitMqConstant.BACKUP_QUEUE_NAME).build();
    }

    @Bean("warningQueue")
    public Queue warningQueue(){
        return QueueBuilder.durable(RabbitMqConstant.WARNING_QUEUE_NAME).build();
    }

    /**
     * 备份绑定
     * @param backupExchange
     * @param backupQueue
     * @return
     */
    @Bean
    public Binding backupQueueBindBackupExchange(
            @Qualifier("backupExchange") FanoutExchange backupExchange
            ,@Qualifier("backupQueue") Queue backupQueue){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    /**
     * 报警绑定
     * @param backupExchange
     * @param warningQueue
     * @return
     */
    @Bean
    public Binding warningQueueBindBackupExchange(
            @Qualifier("backupExchange") FanoutExchange backupExchange
            ,@Qualifier("warningQueue") Queue warningQueue){
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
