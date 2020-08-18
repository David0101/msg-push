package com.jim.msg.push.server.handle;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.jim.msg.push.commons.bean.AjaxResult;
import com.jim.msg.push.commons.constants.CacheKeys;
import com.jim.msg.push.rounter.commons.SocketIOHelper;
import com.jim.msg.push.rounter.dto.SocketIOSessionDto;
import com.jim.msg.push.server.dto.MessageDto;
import com.jim.msg.push.rounter.commons.SocketIOConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 11:11
 */
@Component
@Slf4j
public class SocketEventHandler {
    @Autowired
    private SocketIOHelper helper;
    @Autowired
    private RedisTemplate redisTemplate;


    @OnEvent("sendMsg")
    public void sendMsg(SocketIOClient socketIOClient, AckRequest ackRequest, MessageDto message){
        log.info("接收到[" + SocketIOConst.Req.Event.SEND_MSG_EVENT + "]事件..");
        try {
            log.info("socketIOClient:{},ackRequest{},msg:{}", socketIOClient, ackRequest, message);
            if(message != null){
                String sessionId = socketIOClient.getSessionId().toString();
                SocketIOSessionDto socketIOUserDto = helper.getRedisUser(sessionId);
                if(socketIOUserDto != null){
                    String key = socketIOUserDto.getUserName()+"#"+sessionId;
                    redisTemplate.opsForHash().put(CacheKeys.USER_MSG_MAP,key,message);

                    redisTemplate.expire(CacheKeys.USER_MSG_MAP,CacheKeys.ExpireTime.USER_MSG_MAP_SECOND, TimeUnit.SECONDS);
                    ackRequest.sendAckData(AjaxResult.success());
                }
            }
        }
        catch (Exception e){
            log.error("stopMsgEventException:", e);
        }
        finally {
           helper.resetSessionTime(socketIOClient.getSessionId().toString());
        }
    }
}
