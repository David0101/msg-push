package com.jim.msg.push.rounter.config;

import com.jim.msg.push.commons.constants.RabbitMQConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-18 15:35
 */
@Configuration
public class DirectRabbitConfig {

    @Value("${socketio.rabbitmq.consumer.queue}")
    private String consumerQueue;

    @Bean
    public Queue webSocketMsgRedirectQueue() {
        return new Queue(consumerQueue, true);
    }
    @Bean
    DirectExchange webSocketMsgRedirectExchange() {
        return new DirectExchange(RabbitMQConst.Exchange.Direct.WEB_SOCKET_MSG_REDIRECT_EXCHANGE);
    }
    @Bean
    Binding bindingDirectWebSocketMsgRedirectLocal() {
        return BindingBuilder.bind(webSocketMsgRedirectQueue()).to(webSocketMsgRedirectExchange()).with(consumerQueue);
    }
    @Bean
    Binding bindingDirectWebSocketMsgRedirectAll() {
        return BindingBuilder.bind(webSocketMsgRedirectQueue()).to(webSocketMsgRedirectExchange()).with(RabbitMQConst.RoutingKey.WEB_SOCKET_MSG_REDIRECT_ALL_QUEUE_ROUTING_KEY);
    }
}
