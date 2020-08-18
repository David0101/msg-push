package com.jim.msg.push.rounter.mq;

import com.jim.msg.push.commons.constants.RabbitMQConst;
import com.jim.msg.push.commons.util.JacksonUtil;
import com.jim.msg.push.rounter.dto.SocketIOMsgRedirectDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-18 15:39
 */
@Component
@Slf4j
public class WebSocketMsgRedirectSend {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Async
    public void sendAll(SocketIOMsgRedirectDto message){
        try{
            amqpTemplate.convertAndSend(RabbitMQConst.Exchange.Direct.WEB_SOCKET_MSG_REDIRECT_EXCHANGE
                    ,RabbitMQConst.RoutingKey.WEB_SOCKET_MSG_REDIRECT_ALL_QUEUE_ROUTING_KEY, JacksonUtil.obj2String(message));
        }
        catch (Exception e){
            log.error("WebSocketMsgRedirectSend.sendAll.Exception",e);
        }
    }

    @Async
    public void send(String queue,SocketIOMsgRedirectDto message){
        try{
            amqpTemplate.convertAndSend(RabbitMQConst.Exchange.Direct.WEB_SOCKET_MSG_REDIRECT_EXCHANGE
                    ,queue,JacksonUtil.obj2String(message));
        }catch (Exception e){
            log.error("WebSocketMsgRedirectSend.send.Exception",e);
        }
    }
}
