package com.jim.msg.push.server.handle;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.jim.msg.push.server.common.SocketIOConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-03 15:19
 */
@Component
@Slf4j
public class SocketHandler {

    @OnConnect
    public void onConnect(SocketIOClient socketIOClient){
        String sessionId = socketIOClient.getSessionId().toString();
        String platform = socketIOClient.getHandshakeData().getSingleUrlParam("platform");
        String userId = socketIOClient.getHandshakeData().getSingleUrlParam("userId");
        String accessToken = socketIOClient.getHandshakeData().getSingleUrlParam("accessToken");
        log.info("clent connect");
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient socketIOClient){
        UUID uuid = socketIOClient.getSessionId();
        log.info("登出："+uuid.toString());
    }
}
