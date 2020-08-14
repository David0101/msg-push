package com.jim.msg.push.server.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;


import com.jim.msg.push.commons.util.JacksonUtil;
//import com.jim.msg.push.rounter.cache.RedisClient;
import com.jim.msg.push.rounter.commons.SocketIOHelper;
import com.jim.msg.push.rounter.dto.SocketIOSessionDto;
import com.jim.msg.push.server.common.SocketIOConst;

import com.jim.msg.push.server.dto.SocketIOUserDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisTemplate redisTemplate;
    @OnConnect
    public void onConnect(SocketIOClient socketIOClient){
        String sessionId = socketIOClient.getSessionId().toString();
        //String platform = "test-platform";//socketIOClient.getHandshakeData().getSingleUrlParam("platform");
        String userName = socketIOClient.getHandshakeData().getSingleUrlParam("userName");
        log.info("onConnect : userName:{}", userName);
        if(!StringUtils.isEmpty(userName)){
            SocketIOSessionDto socketIOSessionDto = new SocketIOSessionDto(sessionId,userName,helper.getLocalConsumerQueue());
            String sessionJson = JacksonUtil.obj2String(socketIOSessionDto);
            String sessionIdKey = helper.getCachSessionKey(sessionId);

            redisTemplate.opsForValue().set(sessionIdKey,sessionJson);
            Object test = redisTemplate.opsForValue().get(sessionIdKey);
            SocketIOUserDto socketIOUserDto = new SocketIOUserDto(sessionId,helper.getLocalConsumerQueue());
            String userIdKey = helper.getCacheSessionUserKey(userName);

            redisTemplate.opsForHash().put(userIdKey,sessionId,socketIOUserDto);
            redisTemplate.expire(userIdKey,SocketIOConst.SESSION_TIME_OUT_SECOND, TimeUnit.SECONDS);

            //socketIOClient.joinRoom(platform);

            log.info("session存入redis,{}",sessionJson);
            log.info("session存入redis socketIOUserDto,{}",socketIOUserDto);
        }
        int a;
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient socketIOClient){
        UUID uuid = socketIOClient.getSessionId();
        if(uuid != null){
            String sesssionId = uuid.toString();
            helper.logout(sesssionId);
        }
        log.info("登出："+uuid.toString());
    }
}
