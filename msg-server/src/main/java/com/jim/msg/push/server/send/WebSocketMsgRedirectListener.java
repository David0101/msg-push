package com.jim.msg.push.server.send;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.jim.msg.push.commons.constants.RabbitMQConst;
import com.jim.msg.push.commons.util.JacksonUtil;
import com.jim.msg.push.rounter.commons.SocketIOHelper;
import com.jim.msg.push.rounter.dto.SocketIOMsgRedirectDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-18 16:01
 */
@Component
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${socketio.rabbitmq.consumer.queue}"),
        exchange = @Exchange(value = RabbitMQConst.Exchange.Direct.WEB_SOCKET_MSG_REDIRECT_EXCHANGE, type = ExchangeTypes.DIRECT)))
@Slf4j
public class WebSocketMsgRedirectListener {
    @Autowired
    private SocketIOHelper socketIOHelper;
    @Autowired
    private SocketIOServer socketIOServer;

    @RabbitHandler
    public void process(String message){
        if (StringUtils.isNotBlank(message)) {
            SocketIOMsgRedirectDto redirectDto = JacksonUtil.string2Obj(message,SocketIOMsgRedirectDto.class);
            log.info("接收到重定向消息:{}", message);
            if(redirectDto != null){
                List<SocketIOMsgRedirectDto> response = new ArrayList<>();

            }
        }
    }
}
