package com.shebao.test.config;

import com.shebao.test.constant.WSConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WSConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 注册stomp端点
     * @param registry stomp端点注册对象
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WSConstant.WEBSOCKET_PATH)
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * 配置消息代理
     * @param registry 消息代理注册对象
     * @return
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置服务端推送消息给客户端的代理路径
        registry.enableSimpleBroker(WSConstant.BROKER_QUEUE, WSConstant.BROKER_TOPIC);

        // 定义点对点推送时的前缀为/queue
        registry.setUserDestinationPrefix(WSConstant.BROKER_QUEUE);

        // 定义客户端访问服务端消息接口时的前缀
        registry.setApplicationDestinationPrefixes(WSConstant.WEBSOCKET_PREFIX);
    }
}
