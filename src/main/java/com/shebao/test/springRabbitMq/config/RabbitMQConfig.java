package com.shebao.test.springRabbitMq.config;

import com.google.common.collect.Maps;
import com.shebao.test.constant.RabbitMqConstant;
import com.shebao.test.rabbitMq.six.Constant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;
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


    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) throws IOException {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(20);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));//设置重回队列的次数
        factory.setRetryTemplate(retryTemplate);
        factory.setFailedDeclarationRetryInterval(1000L);//消息多次消费的间隔1秒
        factory.setDefaultRequeueRejected(false);//设置为false，会丢弃消息或者重新发布到死信队列
        factory.setPrefetchCount(5);//设置预拿消息大小
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
}
