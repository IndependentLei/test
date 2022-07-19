package com.shebao.test.springRabbitMq.config;

import com.google.common.collect.Maps;
import com.shebao.test.constant.RabbitMqConstant;
import com.shebao.test.rabbitMq.six.Constant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description :
 * @ClassName : RabbitMQConfig
 * @Author : jdl
 * @Create : 2022-07-11 21:27
 */
@Configuration
public class RabbitMQConfig {

    // 声明X交换机
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(RabbitMqConstant.X_EXCHANGE);
    }

    // 声明Y交换机
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(RabbitMqConstant.Y_DEAD_LETTER_EXCHANGE);
    }

    // 声明队列
    @Bean("queueA")
    public Queue queueA(){
        Map<String,Object> maps = Maps.newHashMap();
        maps.put("x-dead-letter-exchange",RabbitMqConstant.Y_DEAD_LETTER_EXCHANGE); // 设置死信交换机
        maps.put("x-dead-letter-routing-key",RabbitMqConstant.Y_DEAD_LETTER_ROUTING_KEY);// 设置死信息RoutingKey
        maps.put("x-message-ttl", TimeUnit.SECONDS.toMillis(10));
        return QueueBuilder.durable(RabbitMqConstant.QUEUE_A)
                .withArguments(maps).build();
    }

    // 声明队列
    @Bean("queueB")
    public Queue queueB(){
        Map<String,Object> maps = Maps.newHashMap();
        maps.put("x-dead-letter-exchange",RabbitMqConstant.Y_DEAD_LETTER_EXCHANGE); // 设置死信交换机
        maps.put("x-dead-letter-routing-key",RabbitMqConstant.Y_DEAD_LETTER_ROUTING_KEY);// 设置死信息RoutingKey
        maps.put("x-message-ttl", TimeUnit.SECONDS.toMillis(40));
        return QueueBuilder.durable(RabbitMqConstant.QUEUE_B)
                .withArguments(maps).build();
    }

    // 声明队列
    @Bean("queueC")
    public Queue queueC(){
        Map<String,Object> maps = Maps.newHashMap();
        maps.put("x-dead-letter-exchange",RabbitMqConstant.Y_DEAD_LETTER_EXCHANGE); // 设置死信交换机
        maps.put("x-dead-letter-routing-key",RabbitMqConstant.Y_DEAD_LETTER_ROUTING_KEY);// 绑定YD队列
        return QueueBuilder.durable(RabbitMqConstant.QUEUE_C)
                .withArguments(maps).build();
    }

    // 声明死信队列
    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(RabbitMqConstant.DEAD_LETTER_QUEUE).build();
    }

    // 绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,@Qualifier("xExchange") DirectExchange exchange){
        return BindingBuilder.bind(queueA).to(exchange).with(RabbitMqConstant.X_ROUTING_KEY_QA);
    }

    // 绑定
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,@Qualifier("xExchange") DirectExchange exchange){
        return BindingBuilder.bind(queueB).to(exchange).with(RabbitMqConstant.X_ROUTING_KEY_QB);
    }

    // 绑定
    @Bean
    public Binding queueDBindingX(@Qualifier("queueD") Queue queueD,@Qualifier("yExchange") DirectExchange exchange){
        return BindingBuilder.bind(queueD).to(exchange).with(RabbitMqConstant.Y_DEAD_LETTER_ROUTING_KEY);
    }

    // 绑定
    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,@Qualifier("xExchange") DirectExchange exchange){
        return BindingBuilder.bind(queueC).to(exchange).with(RabbitMqConstant.X_ROUTING_KEY_QC);
    }



    // 自定义延迟交换机
    @Bean
    public CustomExchange delayExchange(){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("x-delayed-type","direct"); // 延迟类型为直接类型
        /**
         * 名字
         * 类型
         * 是否持久化
         * 是否自动删除
         * 其他参数
         */
        return new CustomExchange(RabbitMqConstant.DELAY_EXCHANGE_NAME,"x-delayed-message",true,false,paramMap);
    }

    /**
     * 声明延迟队列
     * @return
     */
    @Bean
    public Queue delayQueue(){
        return QueueBuilder.durable(RabbitMqConstant.DELAY_QUEUE_NAME).build();
    }

    /**
     * 绑定
     * @param delayQueue
     * @param delayExchange
     * @return
     */
    @Bean
    public Binding delayQueueBindingDelayExchange(@Qualifier("delayQueue") Queue delayQueue,
                                                  @Qualifier("delayExchange") CustomExchange delayExchange){
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(RabbitMqConstant.DELAY_ROUTING_KEY).noargs();
    }
}
