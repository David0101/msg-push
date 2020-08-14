package com.jim.msg.push.rounter.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jim.msg.push.commons.constants.RabbitMQConst;
import com.jim.msg.push.rounter.dto.SocketIODisconnectMsgSendDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-14 14:56
 */
@Component
@Slf4j
public class WebSocketDisconnectMsgSend {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    @Async
    public void send(String queue, SocketIODisconnectMsgSendDto message) {
        try {
            String uuid = UUID.randomUUID().toString();
            Message mqMessage = MessageBuilder
                    .withBody(objectMapper.writeValueAsBytes(message))
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setMessageId(UUID.randomUUID().toString())
                    .build();
            amqpTemplate.convertAndSend(RabbitMQConst.Exchange.Fanout.WEB_SOCKET_DISCONNECT_EXCAHNGE, queue, mqMessage);
        } catch (Exception e) {
            log.error("WebSocketMsgRedirectSend.send.Exception", e);
        }

    }
}
