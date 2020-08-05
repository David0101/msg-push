package com.jim.msg.push.server.handle;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.jim.msg.push.server.dto.MessageDto;
import com.jim.msg.push.server.common.SocketIOConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 11:11
 */
@Component
@Slf4j
public class SocketEventHandler {

    @OnEvent(SocketIOConst.Req.Event.SEND_MSG_EVENT)
    public void sendMsg(SocketIOClient socketIOClient, AckRequest ackRequest,MessageDto message){
        log.info("接收到:["+SocketIOConst.Req.Event.SEND_MSG_EVENT+ "]事件");
        log.info("socketIOClient:{},ackRequest{},msg:{}", socketIOClient, ackRequest, message);
    }
}
