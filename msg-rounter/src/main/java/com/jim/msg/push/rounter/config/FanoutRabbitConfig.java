package com.jim.msg.push.rounter.config;

import com.jim.msg.push.commons.constants.RabbitMQConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-14 15:02
 */
@Configuration
public class FanoutRabbitConfig {
    @Bean
    public Queue webSocketQueue() {
        return new Queue(RabbitMQConst.Queue.WEB_SOCKET_QUEUE, true, false, false, null);
    }
    @Bean
    FanoutExchange webSocketExchange() {
        return new FanoutExchange(RabbitMQConst.Exchange.Fanout.WEB_SOCKET_EXCHANGE,true,false);
    }
    @Bean
    FanoutExchange webSocketDisconnectExchange(){
        return new FanoutExchange(RabbitMQConst.Exchange.Fanout.WEB_SOCKET_DISCONNECT_EXCAHNGE,true,false);
    }
//    @Bean
//    Binding bindingFanoutWebSocket() {
//        return BindingBuilder.bind(webSocketQueue()).to(webSocketExchange());
//    }
    @Bean
    Binding bindingFanoutWebSocketDisconnect() {
        return BindingBuilder.bind(webSocketQueue()).to(webSocketDisconnectExchange());
    }

}
