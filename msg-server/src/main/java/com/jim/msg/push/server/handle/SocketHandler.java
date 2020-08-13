package com.jim.msg.push.server.handle;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;


import com.jim.msg.push.commons.util.JacksonUtil;
import com.jim.msg.push.rounter.cache.RedisClient;
import com.jim.msg.push.rounter.commons.SocketIOHelper;
import com.jim.msg.push.server.common.SocketIOConst;
import com.jim.msg.push.server.dto.SocketIOSessionDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-03 15:19
 */
@Component
@Slf4j
public class SocketHandler {

    @Autowired
    private SocketIOHelper helper;
    @OnConnect
    public void onConnect(SocketIOClient socketIOClient){
        String sessionId = socketIOClient.getSessionId().toString();
        String userName = socketIOClient.getHandshakeData().getSingleUrlParam("userName");
        log.info("onConnect : userName:{}", userName);
        if(!StringUtils.isEmpty(userName)){
            SocketIOSessionDto socketIOSessionDto = new SocketIOSessionDto(sessionId,userName,helper.getLocalConsumerQueue());
            String sessionJson = JacksonUtil.obj2String(socketIOSessionDto);
            String sessionIdKey = helper.getCachSessionKey(sessionId);
            String jsonStr = RedisClient.getValue( "yunzhangxiao:department:userId:444");
            RedisClient.setValue(sessionIdKey,sessionJson, SocketIOConst.SESSION_TIME_OUT_SECOND);
            int a = 0;
        }
        int a;
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient socketIOClient){
        UUID uuid = socketIOClient.getSessionId();
        log.info("登出："+uuid.toString());
    }
}
